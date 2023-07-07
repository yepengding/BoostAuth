# Next Cloud Integration

## Steps

1. Enable [External user authentication](https://apps.nextcloud.com/apps/user_external)
2. Overwrite `Base.php` and `BasicAuth.php` with the provided ones in `extra-apps/user_external/lib`.
3. Add the following to `config.php`:

```
  'user_backends' => array(
    array(
        'class' => '\OCA\UserExternal\BasicAuth',
        'arguments' => array('http://host.docker.internal:9000/basic/login'),
    ),
  ),
```