/**
 * This test script requires Node.js v18+
 *
 */

const AuthClient = require("./AuthClient.js")

const client = new AuthClient("http://localhost:9000");
client.login("ypding", "ypding", "test_group")
    .then(res => {
        console.log(res)
    });