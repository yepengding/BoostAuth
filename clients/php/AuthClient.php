<?php

/**
 * Authentication Client.
 * PHP 7.4+
 *
 * @author Yepeng Ding
 */
class AuthClient
{
    /**
     * @var string $domain
     */
    private string $domain;

    /**
     * Construct a new 'AuthClient' object.
     *
     * @param string $domain BoostAuth domain (e.g., boostauth.org)
     */
    public function __construct(string $domain)
    {
        $this->domain = $domain;
    }

    /**
     * Login interface.
     *
     * @param string $username username
     * @param string $password password
     * @param string $source source
     * @return mixed PHP object
     */
    public function login(string $username, string $password, string $source): mixed
    {
        return $this->post($this->domain . '/auth/login', array(
            'username' => $username,
            'password' => $password,
            'source' => $source
        ));
    }

    /**
     * Logout interface.
     *
     * @param string $token token obtained after login
     * @return mixed PHP object
     */
    public function logout(string $token): mixed
    {
        return $this->post($this->domain . '/auth/logout', array(
            'token' => $token
        ));
    }

    /**
     * Verify token interface.
     *
     * @param string $token token obtained after login
     * @return mixed PHP object
     */
    public function verify(string $token): mixed
    {
        return $this->post($this->domain . '/verify', array(
            'token' => $token
        ));
    }

    /**
     * Post method.
     *
     * @param string $url url
     * @param array $data request payload
     * @return mixed PHP object
     */
    private function post(string $url, array $data): mixed
    {
        $options = array(
            'http' => array(
                'header' => "Content-Type: application/json\r\n" . "Accept: application/json\r\n",
                'method' => 'POST',
                'content' => json_encode($data)
            )
        );

        $context = stream_context_create($options);
        $result = file_get_contents($url, false, $context);

        return json_decode($result);
    }
}