# User Behavior
## Behavior
- The record of user behavior data includes following, likes, dislikes, favorites, reading and other behaviors

- The whole project development of Dark Horse Headlines project involves web display and big data analysis to recommend articles to users. How to find out which articles are hot articles for targeted recommendation? At this time, we need to prepare for big data analysis and bury the point.

- The so-called "buried point" is a term in the field of data acquisition (especially in the field of user behavior data collection), which refers to the relevant technology and its implementation process for capturing, processing and sending specific user behaviors or events. For example, the number of clicks of a user icon, the time of reading articles, the time of watching videos, and so on.

## Like
|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/likes_behavior |
| Request Method | POST                 |
| Parameters     | LikesBehaviorDto         |
| Response Result | ResponseResult       |

## Read
|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/read_behavior |
| Request Method | POST                 |
| Parameters     | ReadBehaviorDto         |
| Response Result | ResponseResult       |

## Unlike
|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/un_likes_behavior |
| Request Method | POST                 |
| Parameters     | UnLikesBehaviorDto         |
| Response Result | ResponseResult       |

## Follow and unfollow
|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/user/user_follow |
| Request Method | POST                 |
| Parameters     | UserRelationDto         |
| Response Result | ResponseResult       |

## Collection
|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/collection_behavior |
| Request Method | POST                 |
| Parameters     | CollectionBehaviorDto         |
| Response Result | ResponseResult       |

## Data echo
|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/article/load_article_behavior |
| Request Method | POST                 |
| Parameters     | ArticleInfoDto         |
| Response Result | ResponseResult       |