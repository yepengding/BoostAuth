"""
Authentication Client

:author Yepeng Ding
"""
import requests


class AuthClient:
    """
    Authentication Client contains APIs to interact with BoostAuth.
    """

    def __init__(self, domain: str):
        """
        Construct a new 'AuthClient' object.

        :param domain: BoostAuth domain (e.g., boostauth.org)
        """
        self.domain = domain

    def login(self, username: str, password: str, source: str) -> dict:
        """
        Login interface.

        :param username: username
        :param password: password
        :param source: source
        :return: JSON response
        """
        return requests.post(f'{self.domain}/auth/login', json={
            "username": username,
            "password": password,
            "source": source}).json()

    def logout(self, token: str) -> dict:
        """
        Logout interface.

        :param token: token obtained after login
        :return: JSON response
        """
        return requests.post(f'{self.domain}/auth/logout', json={
            "token": token}).json()

    def verify(self, token: str) -> dict:
        """
        Verify interface.

        :param token: token obtained after login
        :return: JSON response
        """
        return requests.post(f'{self.domain}/verify', json={
            "token": token}).json()
