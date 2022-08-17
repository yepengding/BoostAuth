from auth_client import AuthClient

# Instantiate a client
client = AuthClient("http://localhost:9000")

# Login a test account
res = client.login("ypding", "ypding", "test_group")
# Verify the token
res = client.verify(res['data'])

print(res)
