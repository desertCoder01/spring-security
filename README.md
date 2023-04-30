# spring-security
A demo project demonstrating use of Spring security
No database connection is required in this case , but you can use it for fetching and saving user details in database
Where ever db is required , i have put a comment there , which can be modified according to requirement

Explanation : 
 1. Maven project ( Build tool , same code base will work for gradle as well with proper dependency)
 2. Spring security project , which can be enhanced to Oauth2 
 3. Accessing any api with Authorized token and developing some api with free from security
 4. Putting a role check on api , to be accessed by user having specified roles
 5. Putting a permission check on api , to be accessed by user having that specified permissions.

below are  some curl for better understanding : 

customer login curl : (With role customer and permission read , you can modify these two details accordingly)

curl --location --request POST 'localhost:8080/auth/login/customer' \
--header 'Cookie: JSESSIONID=9ADE573662F3C02288F76B8BF44232E8'

response :
{
"accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWthc2gua3VtYXJAZ21haWwuY29tIiwiZXhwIjoxNjgzNzIzMDQzLCJpYXQiOjE2ODI4NTkwNDN9.j9fFDMI1clgtKOVe5ptDT_HjLaA538ZFSszVVBuoiyk",
"tokenType": "Bearer",
"refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWthc2gua3VtYXJAZ21haWwuY29tIiwiZXhwIjoxNjg0MzMwMjcyLCJpYXQiOjE2ODI4NTkwNDN9.SbGKaFZePI_4FnTWSlKP-XN3U8R3uut9AvZ58n3ngQQ"
}

admin login curl :(with role admin and with permission read , write and delete) 

curl --location --request POST 'localhost:8080/auth/login/admin' \
--header 'Cookie: JSESSIONID=9ADE573662F3C02288F76B8BF44232E8'


try to hit this curl : (secured for admin but trying to hit using customer role token )

curl --location --request GET 'localhost:8080/user/admin-list' \
--header 'x-auth-token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aWthc2gua3VtYXJAZ21haWwuY29tIiwiZXhwIjoxNjgzNzIzMDQzLCJpYXQiOjE2ODI4NTkwNDN9.j9fFDMI1clgtKOVe5ptDT_HjLaA538ZFSszVVBuoiyk' \
--header 'Cookie: JSESSIONID=AA4686E99EF645376F9FC5BF3BB14940'

response : status code should be 403 (UNAUTHORIZED_ACCESS)

try to hit the below curl : (secured for admin and hitting with admin token)

curl --location --request GET 'localhost:8080/user/admin-list' \
--header 'x-auth-token: eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwYW5rYWoua3VtYXJAZ21haWwuY29tIiwiZXhwIjoxNjgzNzIzMTI4LCJpYXQiOjE2ODI4NTkxMjh9.yhdqAQ-2sVFCzRtG4V92uWUDc3x7xPP569po33hM6Ew' \
--header 'Cookie: JSESSIONID=AA4686E99EF645376F9FC5BF3BB14940'

response : status code 200








