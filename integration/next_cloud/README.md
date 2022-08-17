# Next Cloud Integration

## Steps

1. Enable [External user authentication](https://apps.nextcloud.com/apps/user_external)
2. Overwrite `BasicAuth.php` with the provided one.
3. Add the following to `config.php`:

```
  'user_backends' => array(
    array(
        'class' => '\OCA\UserExternal\BasicAuth',
        'arguments' => array('http://host.docker.internal:9000/basic/login'),
    ),
  ),
```