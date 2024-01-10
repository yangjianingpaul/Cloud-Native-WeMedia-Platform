# App登陆
## 注册->生成盐
![](/resources/盐值.png)
## 手动加密（md5+随机字符串）
![](/resources/App登陆.png)
* 用户输入了用户名和密码进行登录，校验成功后返回jwt(基于当前用户的id生成)
* 用户游客登录，生成jwt返回(基于默认值0生成)
## 接口定义
|            |          说明             |
| :--------: | :----------------------: |
|  接口路径    | /api/v1/login/login_auth |
|  请求方式    |          POST            |
|    参数     |         LoginDto         |
|   响应结果   |      ResponseResult      |
