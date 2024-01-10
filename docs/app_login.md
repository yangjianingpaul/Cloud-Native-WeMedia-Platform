# App Login
## Login->Generate Salt
![](/resources/salt.png)
## Manual encryption (md5+random string)
![](/resources/AppLogin.png)
* The user enters the user name and password to log in, and returns the jwt(generated based on the current user id) after the verification is successful.
* User visitor login, generate jwt return (generated based on default value 0)
## Interface Definition
|                    |      Illustrate          |
| :----------------: | :----------------------: |
|  Interface Path    | /api/v1/login/login_auth |
|  Request Method    |          POST            |
|    Parameters      |         LoginDto         |
|   Response Result  |      ResponseResult      |