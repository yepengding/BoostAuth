<?php require "AuthClient.php";

// Instantiate a client
$client = new AuthClient("http://localhost:9000");

// Login a test account
$res = $client->login("ypding", "ypding", "test_group");

// Verify the token
$res = $client->verify($res->data);

echo $res->data;
