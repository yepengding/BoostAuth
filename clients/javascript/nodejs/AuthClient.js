/**
 * Authentication Client for Node.js.
 * ECMAScript 5
 *
 * @author Yepeng Ding
 */
module.exports = class AuthClient {

    /**
     * Construct a new 'AuthClient' object.
     *
     * @param domain BoostAuth domain (e.g., boostauth.org)
     */
    constructor(domain) {
        this.domain = domain;
    }

    /**
     * Login interface.
     *
     * @param username username
     * @param password password
     * @param source source
     * @returns {Promise<any>} promise object
     */
    login(username, password, source) {
        return AuthClient.#post(this.domain + "/token/auth/login", {
            username,
            password,
            source
        });
    }

    /**
     * Logout interface.
     *
     * @param token token obtained after login
     * @returns {Promise<any>} promise object
     */
    logout(token) {
        return AuthClient.#post(this.domain + "/token/auth/logout", {
            token
        });
    }

    /**
     * Verify token interface.
     *
     * @param token token
     * @returns {Promise<any>} promise object
     */
    verify(token) {
        return AuthClient.#post(this.domain + "/token/verify", {
            token
        });
    }

    /**
     * Post method.
     *
     * @param url url
     * @param data request payload
     * @returns {Promise<any>} promise object
     */
    static #post(url, data) {
        return fetch(url, {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(data)
        }).then(res => res.json());
    }

}