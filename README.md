README
======

Introduction
------------

This project was created with the intention of understanding new languages,
technologies and combining them. The primary goal is to learn Scala. A fair
critic would be to point out other languages which would have made this project
a whole lot easier, but again, this was not the objective.

We are trying to create a very (**very!**) simple framework following the ***MVC***
design pattern in Scala and combining them with small webapps using different
technologies.

**Note:** the website is running in CGI. We are aware of the wasted resources!

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
