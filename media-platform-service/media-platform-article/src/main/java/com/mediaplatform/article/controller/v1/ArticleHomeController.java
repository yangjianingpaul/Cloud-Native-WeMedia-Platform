package com.mediaplatform.article.controller.v1;

import com.mediaplatform.article.service.impl.ApArticleServiceImpl;
import com.mediaplatform.common.constants.ArticleConstants;
import com.mediaplatform.model.article.dtos.ArticleHomeDto;
import com.mediaplatform.model.common.dtos.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Article Home Controller - REST API for article feed and home page content
 * 
 * This controller manages the main article feed functionality for the media platform,
 * providing paginated article content for mobile and web clients. It implements
 * efficient content loading strategies to optimize user experience and system performance.
 * 
 * Key functionalities:
 * - Initial article feed loading with personalized recommendations
 * - Pagination support for loading more articles (scroll-down)
 * - Real-time updates for loading latest articles (pull-to-refresh)
 * - Channel-based article filtering and categorization
 * - User preference-based content personalization
 * 
 * Content Loading Strategies:
 * - Load: Initial page load with recommendations and hot articles
 * - Load More: Pagination for older articles as user scrolls
 * - Load New: Refresh functionality for latest published content
 * 
 * Performance optimizations:
 * - Cached article metadata for faster response times
 * - Efficient database queries with proper indexing
 * - Content pre-loading and lazy loading strategies
 * 
 * API Endpoints:
 * - POST /api/v1/article/load: Initial article feed loading
 * - POST /api/v1/article/loadmore: Load more articles (pagination)
 * - POST /api/v1/article/loadnew: Load latest articles (refresh)
 * 
 * @author Media Platform Team
 * @version 1.0
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/v1/article")
public class ArticleHomeController {

    @Autowired
    private ApArticleServiceImpl apArticleService;

    /**
     * Load initial article feed for home page
     * 
     * This endpoint handles the initial loading of articles when users first access
     * the home page or app. It provides a curated mix of:
     * - Hot/trending articles based on user engagement
     * - Personalized recommendations based on user preferences
     * - Latest articles from followed channels/authors
     * - Popular content from user's preferred categories
     * 
     * The method uses an enhanced loading algorithm (load2) that considers
     * user context and provides optimized content selection.
     * 
     * @param dto ArticleHomeDto containing loading parameters (channel, timestamp, etc.)
     * @return ResponseResult containing paginated article list with metadata
     */
    @PostMapping("/load")
    public ResponseResult load(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load2(dto, ArticleConstants.LOADTYPE_LOAD_MORE, true);
    }

    /**
     * Load more articles for pagination
     * 
     * This endpoint supports infinite scroll functionality by loading older articles
     * when users scroll down past the current content. It maintains continuity
     * in the article feed and provides seamless browsing experience.
     * 
     * Loading strategy:
     * - Fetches articles older than the last loaded timestamp
     * - Maintains user's current channel/category filter
     * - Applies same personalization rules as initial load
     * - Supports configurable page sizes for optimal performance
     * 
     * @param dto ArticleHomeDto with timestamp of last loaded article for pagination
     * @return ResponseResult containing next page of articles in chronological order
     */
    @PostMapping("/loadmore")
    public ResponseResult loadMore(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_MORE);
    }

    /**
     * Load latest articles for refresh functionality
     * 
     * This endpoint supports pull-to-refresh functionality by loading newly published
     * articles since the user's last visit. It ensures users see the most current
     * content and maintains engagement with fresh material.
     * 
     * Loading strategy:
     * - Fetches articles newer than the current top article timestamp
     * - Prioritizes breaking news and high-engagement content
     * - Maintains user's channel preferences and filters
     * - Provides real-time content updates
     * 
     * @param dto ArticleHomeDto with timestamp of newest currently loaded article
     * @return ResponseResult containing latest published articles since last refresh
     */
    @PostMapping("/loadnew")
    public ResponseResult loadNew(@RequestBody ArticleHomeDto dto) {
        return apArticleService.load(dto, ArticleConstants.LOADTYPE_LOAD_NEW);
    }
}
