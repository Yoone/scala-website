README
======

Introduction
------------

TODO

Installation
------------

### Set Scala path

Use the following command: `echo $SCALA_BIN > website/whereisscala`

Where `$SCALA_BIN` is the directory in which the `scala` binary is located.

### Apache configuration

Activate the following Apache2 modules using `sudo a2enmod <module>`:

- cgi
- rewrite

Then add a new Apache configuration file in `sites-available` containing the following:

    <VirtualHost *:80>
        ServerAdmin your@email.tld
        ServerName scala.local # Needs to be added to your /etc/hosts
        DocumentRoot /path/to/scala-website/website
        <Directory /path/to/scala-website/website>
            Options +ExecCGI
            AddHandler cgi-script .sh
            RewriteEngine On
            RewriteCond $1 !^(index\.sh|css/|img/|js/|robots\.txt)
            RewriteRule ^(.*)$ index.sh/$1 [L]
            Require all granted
        </Directory>
    </VirtualHost>

Now activate the new website: `sudo a2ensite <your conf file>`

Restart Apache: `sudo service apache2 restart`

### Hosts file

For the website to be available through something like `http://scala.local`, just add this line to `/etc/hosts`:

    127.0.0.1    scala.local

### Deploy website

Use the provided deploy script: `./deploy.sh`
