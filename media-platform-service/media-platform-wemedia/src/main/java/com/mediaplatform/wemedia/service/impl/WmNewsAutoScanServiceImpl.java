package com.mediaplatform.wemedia.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mediaplatform.apis.article.IArticleClient;
import com.mediaplatform.common.baidu.GreenImageScan;
import com.mediaplatform.common.baidu.GreenTextScan;
import com.mediaplatform.common.tess4j.Tess4jClient;
import com.mediaplatform.file.service.FileStorageService;
import com.mediaplatform.model.article.dtos.ArticleDto;
import com.mediaplatform.model.common.dtos.ResponseResult;
import com.mediaplatform.model.wemedia.pojos.WmChannel;
import com.mediaplatform.model.wemedia.pojos.WmNews;
import com.mediaplatform.model.wemedia.pojos.WmSensitive;
import com.mediaplatform.model.wemedia.pojos.WmUser;
import com.mediaplatform.utils.common.SensitiveWordUtil;
import com.mediaplatform.wemedia.mapper.WmChannelMapper;
import com.mediaplatform.wemedia.mapper.WmNewsMapper;
import com.mediaplatform.wemedia.mapper.WmSensitiveMapper;
import com.mediaplatform.wemedia.mapper.WmUserMapper;
import com.mediaplatform.wemedia.service.WmNewsAutoScanService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * WeMedia News Auto-Scan Service Implementation - Automated content moderation and review system
 * 
 * This service provides comprehensive automated content moderation for user-submitted articles
 * in the WeMedia platform. It implements a multi-layered content review system to ensure
 * platform safety and compliance with content policies.
 * 
 * Key moderation capabilities:
 * - Automated sensitive word filtering using custom dictionary
 * - AI-powered text content analysis via Baidu AI services
 * - Image content analysis and OCR text extraction
 * - Multi-stage review process with different approval statuses
 * - Integration with article publishing pipeline
 * - Asynchronous processing for performance optimization
 * 
 * Content Review Pipeline:
 * 1. Extract text content and images from article
 * 2. Apply sensitive word filtering using local dictionary
 * 3. Submit text content to Baidu AI for comprehensive analysis
 * 4. Process images through OCR and AI image analysis
 * 5. Update article status based on review results
 * 6. Publish approved articles to the main platform
 * 
 * Review Status Codes:
 * - Status 2: Rejected due to illegal/inappropriate content
 * - Status 3: Pending manual review (uncertain content)
 * - Status 9: Approved and published successfully
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@Service
@Slf4j
@Transactional
public class WmNewsAutoScanServiceImpl implements WmNewsAutoScanService {

    @Autowired
    private WmNewsMapper wmNewsMapper;

    /**
     * Automated content moderation and review for WeMedia articles
     * 
     * This is the main entry point for the automated content review system.
     * It orchestrates the entire content moderation pipeline including:
     * - Article validation and status verification
     * - Text and image extraction from article content
     * - Multi-layered content analysis and filtering
     * - Article publishing upon successful review
     * 
     * The process is designed to be asynchronous to avoid blocking the user
     * interface while performing intensive AI-based content analysis.
     * 
     * Processing Flow:
     * 1. Validate article exists and is in submitted status
     * 2. Extract content and images from article JSON
     * 3. Apply sensitive word filtering
     * 4. Perform AI-based text content analysis
     * 5. Process images through OCR and AI analysis
     * 6. Publish approved articles to main platform
     * 
     * @param id the unique identifier of the WeMedia article to review
     * @throws RuntimeException if article doesn't exist or processing fails
     */
    @Override
    @Async  // Asynchronous processing to improve user experience
    public void autoScanWmNews(Integer id) {
        try {
            // Simulate processing delay (can be removed in production)
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        log.info("Starting automated content review for article ID: {}", id);
        
        // Step 1: Validate article exists and retrieve article data
        WmNews wmNews = wmNewsMapper.selectById(id);
        if (wmNews == null) {
            log.error("Article review failed: Article with ID {} does not exist", id);
            throw new RuntimeException("WmNewsAutoScanServiceImpl-Article does not exist");
        }

        // Only process articles that are in submitted status
        if (wmNews.getStatus().equals(WmNews.Status.SUBMIT.getCode())) {
            log.info("Processing article in submitted status: {}", wmNews.getTitle());
            
            // Step 2: Extract and parse content for analysis
            Map<String, Object> textAndImages = handleTextAndImages(wmNews);

            // Step 3: Apply sensitive word filtering (first line of defense)
            boolean isSensitive = handleSensitiveScan((String) textAndImages.get("content"), wmNews);
            if (!isSensitive) {
                log.warn("Article {} failed sensitive word screening", id);
                return;
            }

            // Step 4: Perform AI-based text content analysis using Baidu services
            String content = (String) textAndImages.get("content");
            if (StringUtils.isNotBlank(content)) {
                boolean isTextScan = handleTextScan(content, wmNews);
                if (!isTextScan) {
                    log.warn("Article {} failed AI text content analysis", id);
                    return;
                }
            }

            // Step 5: Process images through OCR and AI image analysis
            List<String> images = (List<String>) textAndImages.get("images");
            if (images != null && images.size() > 0) {
                boolean isImageScan = handleImageScan(images, wmNews);
                if (!isImageScan) {
                    log.warn("Article {} failed image content analysis", id);
                    return;
                }
            }
        }

        // Step 6: Publish approved article to the main application platform
        log.info("All content reviews passed, publishing article {} to main platform", id);
        ResponseResult responseResult = saveAppArticle(wmNews);
        if (!responseResult.getCode().equals(200)) {
            log.error("Failed to publish article {} to main platform", id);
            throw new RuntimeException("WmNewsAutoScanServiceImpl-Failed to save related article data on the app");
        }
        
        // Update article with published article ID and mark as successfully reviewed
        wmNews.setArticleId((Long) responseResult.getData());
        updateWmNews(wmNews, 9, "Successful audit");
        log.info("Article {} successfully reviewed and published with article ID: {}", id, responseResult.getData());
    }

    @Autowired
    private WmSensitiveMapper wmSensitiveMapper;

    /**
     * Performs sensitive word filtering using custom dictionary
     * 
     * This method implements the first line of defense in content moderation by
     * checking article content against a maintained list of sensitive/prohibited words.
     * It uses an efficient trie-based algorithm for fast pattern matching.
     * 
     * Process:
     * 1. Retrieves current sensitive word dictionary from database
     * 2. Initializes high-performance word matching engine
     * 3. Scans content for any prohibited terms
     * 4. Updates article status if violations are found
     * 
     * @param content the text content to be analyzed for sensitive words
     * @param wmNews the article entity to update if violations are found
     * @return true if content passes sensitive word check, false if violations found
     */
    private boolean handleSensitiveScan(String content, WmNews wmNews) {
        boolean flag = true;
        
        log.debug("Starting sensitive word analysis for article: {}", wmNews.getId());
        
        // Retrieve current sensitive word dictionary from database
        List<WmSensitive> wmSensitives = wmSensitiveMapper.selectList(
            Wrappers.<WmSensitive>lambdaQuery().select(WmSensitive::getSensitives)
        );
        List<String> sensitiveList = wmSensitives.stream()
            .map(WmSensitive::getSensitives)
            .collect(Collectors.toList());
            
        // Initialize high-performance sensitive word matching engine
        SensitiveWordUtil.initMap(sensitiveList);
        
        // Scan article content for sensitive word matches
        Map<String, Integer> matchedWords = SensitiveWordUtil.matchWords(content);
        if (matchedWords.size() > 0) {
            log.warn("Sensitive words detected in article {}: {}", wmNews.getId(), matchedWords);
            updateWmNews(wmNews, 2, "Illegal content detected: " + matchedWords);
            flag = false;
        } else {
            log.debug("Article {} passed sensitive word screening", wmNews.getId());
        }
        
        return flag;
    }

    @Autowired
    private IArticleClient articleClient;

    @Autowired
    private WmChannelMapper wmChannelMapper;

    @Autowired
    private WmUserMapper wmUserMapper;

    /**
     * save the relevant article data on the app
     *
     * @param wmNews
     */
    private ResponseResult saveAppArticle(WmNews wmNews) {
        ArticleDto dto = new ArticleDto();
//        copy property
        BeanUtils.copyProperties(wmNews, dto);
//        the layout of the article
        dto.setLayout(wmNews.getType());
//        channel
        WmChannel wmChannel = wmChannelMapper.selectById(wmNews.getChannelId());
        if (wmChannel != null) {
            dto.setChannelName(wmChannel.getName());
        }
//        author
        dto.setAuthorId(wmNews.getUserId().longValue());
        WmUser wmUser = wmUserMapper.selectById(wmNews.getUserId());
        if (wmUser != null) {
            dto.setAuthorName(wmUser.getName());
        }

//        set article id
        if (wmNews.getArticleId() != null) {
            dto.setId(wmNews.getArticleId());
        }
        dto.setCreatedTime(new Date());
        ResponseResult responseResult = articleClient.saveArticle(dto);
        return responseResult;
    }

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private Tess4jClient tess4jClient;

    /**
     * Review picture
     *
     * @param images
     * @param wmNews
     * @return
     */
    private boolean handleImageScan(List<String> images, WmNews wmNews) {
        boolean flag = true;
        if (images == null || images.size() == 0) {
            return flag;
        }
//        Download images from minio
        images = images.stream().distinct().collect(Collectors.toList());
        List<byte[]> imageList = new ArrayList<>();

        try {
            for (String image : images) {
                byte[] bytes = fileStorageService.downLoadFile(image);
//        Tess4j:
                ByteArrayInputStream in = new ByteArrayInputStream(bytes);
                BufferedImage bufferedImage = ImageIO.read(in);

//                Picture recognition
                String result = tess4jClient.doOCR(bufferedImage);
                if (!result.equals(null) && result.length() != 0) {
//                Filter text
                    boolean isSensitive = handleSensitiveScan(result, wmNews);
                    if (!isSensitive) {
                        return isSensitive;
                    }
                }
                imageList.add(bytes);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


//        Review picture
        try {
            for (byte[] bytes : imageList) {
                Map map = greenImageScan.imageScan(bytes);

                if (map != null) {
//                审核失败
                    if (map.get("conclusion").equals("不合规")) {
                        updateWmNews(wmNews, 2, "There is illegal content in the current article");
                        flag = false;
                        break;
                    }

//                Uncertain information, manual review required
                    if (map.get("conclusion").equals("审核失败") || map.get("conclusion").equals("疑似")) {
                        updateWmNews(wmNews, 3, "Uncertain information, manual review required");
                        flag = false;
                        break;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return flag;
    }

    @Autowired
    private GreenTextScan greenTextScan;

    /**
     * Review plain text content
     *
     * @param content
     * @param wmNews
     * @return
     */
    private boolean handleTextScan(String content, WmNews wmNews) {

        boolean flag = true;

        if ((wmNews.getTitle() + "-" + content).length() == 1) {
            return flag;
        }

        try {
            Map map = greenTextScan.textScan(content);
            if (map != null) {
//                Audit failure
                if (map.get("conclusion").equals("不合规")) {
                    updateWmNews(wmNews, 2, "There is illegal content in the current article");
                    flag = false;
                }

//                Uncertain information, manual review required
                if (map.get("conclusion").equals("审核失败") || map.get("conclusion").equals("疑似")) {
                    updateWmNews(wmNews, 3, "Uncertain information, manual review required");
                    flag = false;
                }
            }
        } catch (Exception e) {
            flag = false;
            throw new RuntimeException(e);
        }
        return flag;
    }

    /**
     * Revise article content
     *
     * @param wmNews
     * @param status
     * @param reason
     */
    private void updateWmNews(WmNews wmNews, int status, String reason) {
        wmNews.setStatus((short) status);
        wmNews.setReason(reason);
        wmNewsMapper.updateById(wmNews);
    }

    /**
     * 1。Extract text and pictures from the content of we-media articles
     * 2。Extract the cover image of the article
     *
     * @param wmNews
     * @return
     */
    private Map<String, Object> handleTextAndImages(WmNews wmNews) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> images = new ArrayList<>();
//        1。Extract text and pictures from the content of we-media articles
        if (StringUtils.isNotBlank(wmNews.getContent())) {
            List<Map> maps = JSONArray.parseArray(wmNews.getContent(), Map.class);
            for (Map map : maps) {
                if (map.get("type").equals("text")) {
                    stringBuilder.append(map.get("value"));
                }

                if (map.get("type").equals("image")) {
                    images.add((String) map.get("value"));
                }
            }
        }

//        2。Extract the cover image of the article
        if (StringUtils.isNotBlank(wmNews.getImages())) {
            String[] split = wmNews.getImages().split(",");
            images.addAll(Arrays.asList(split));
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("content", stringBuilder.toString());
        resultMap.put("images", images);
        return resultMap;
    }
}
