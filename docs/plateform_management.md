# Platform Management
## Platform Management Interface
![](/resources/platformManagement.png)

## Platform management login
|          | **Introduction**             |
| -------- | -------------------- |
| Interface path | /admin/login/in |
| Request Method | POST                 |
| Parameter     | AdUserDto                   |
| Response Result | ResponseResult       |

## Channel Management

- Create Channel

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/channel/save |
| Request Method | POST                 |
| Parameter     | WmChannel                   |
| Response Result | ResponseResult       |

- Query channel list

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/channel/list |
| Request Method | POST                 |
| Parameter     | ChannelDto                   |
| Response Result | ResponseResult       |

- Update Channel

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/channel/update |
| Request Method | POST                 |
| Parameter     | WmChannel                   |
| Response Result | ResponseResult       |

- Delete Channel

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/channel/del/{id} |
| Request Method | GET                 |
| Parameter     | Null                   |
| Response Result | ResponseResult       |

## Illegal words management

- Create illegal words

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/sensitive/save |
| Request Method | POST                 |
| Parameter     | WmSensitive                   |
| Response Result | ResponseResult       |

- Query illegal words

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/sensitive/list |
| Request Method | POST                 |
| Parameter     | SensitiveDto                   |
| Response Result | ResponseResult       |

- Update illegal words

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/sensitive/update |
| Request Method | POST                 |
| Parameter     | WmSensitive                   |
| Response Result | ResponseResult       |

- Delete illegal words

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/sensitive/del/{id} |
| Request Method | DELETE                 |
| Parameter     | Null                   |
| Response Result | ResponseResult   ｜

## User authentication audit

- Personal center users in the app can be real-name authentication, and the required materials are: name, ID number, ID card front photo, ID card back photo, handheld photo, and living photo (through the combination of smiling, blinking, opening mouth, shaking head, nodding, etc., to ensure that the operation is a real living face.) When the user submits the review, it goes to the back end for the operations manager to conduct the review
- The platform operator checks the user authentication information for audit, including user identity audit, and needs to connect to the public security system to verify ID card information
- After passing the audit, the user needs to open a we-media account (the user name and password of the account are the same as that of the app)

* User audit list

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/auth/list |
| Request Method | POST                 |
| Parameter     | AuthDto                   |
| Response Result | ResponseResult       |

* Pass user review

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/auth/authPass |
| Request Method | POST                 |
| Parameter     | AuthDto                   |
| Response Result | ResponseResult       |

* Reject user audit

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/auth/authFail |
| Request Method | POST                 |
| Parameter     | AuthDto                   |
| Response Result | ResponseResult       |

## Manual article review

- If the we-media article fails to be automatically audited, but is manually audited (the status of the we-media article is 3), you need to manually process the review of the article on the admin side

    - The platform administrator can check the manually audited article information, and if there is any illegal content, it will be rejected (the status is changed to 2, the article review fails).
    - The platform administrator can check the manually reviewed article information, if there is no violation, it needs to create the article information on the app side, and update the status of the media article
- You can also click the view button to view the details of the article, and you can judge whether it needs to pass the audit according to the content after viewing the details

- Article list query
    - Paging to query we-media articles
    - You can fuzzy query by title
    - Accurate search based on audit status
    - Articles are queried in reverse order of creation time
    - Note: The author name needs to be displayed

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/news/list_vo |
| Request Method | POST                 |
| Parameter     | NewsAuthDto         |
| Response Result | ResponseResult       |

- Find article details
    - See article details
    - Note: The author name needs to be displayed

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/news/one_vo/{id} |
| Request Method | GET                 |
| Parameter     | Null         |
| Response Result | ResponseResult       |

- manual review
    - reject
    - Successful audit

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/news/auth_fail |
| Request Method | POST                 |
| Parameter     | NewsAuthDto         |
| Response Result | ResponseResult       |

|          | **Introduction**             |
| -------- | -------------------- |
| Interface Path | /api/v1/news/auth_pass |
| Request Method | POST                 |
| Parameter     | NewsAuthDto         |
| Response Result | ResponseResult       |