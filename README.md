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

Know Issues
-----------

### File upload

If you would like to upload a file (eg: an image for the ascii converter) you
need to make sure that the image field of the page is the very last in the
form. If it is not the file will not be stored correctly and the fields after
will be lost. The reason for this is that we start to readbyte as soon as we
detect a file is being transmitted, in order to know if we have reached the
end of the file and switch back to parsing we would need to convert each byte
into a char. This operation is very long and therefore we have chosen to
ignore this scenario. It is interesting to note that there might be an error
in the scala library for the Stdin readByte() method that does a readline only
to try and convert a **line** into a byte (this was discovered after many
hours of headbanging...).

Installation
------------

### SBT Plugins

Add the following line to `project/plugins.sbt`:

    addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.13.0")

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

### Database

MySQL >= 5.5 is needed for this project to work. You will first need to create a database and a user with all permissions on it.

- Log into MySQL: `mysql -u root -p` and then type your root password
- Create a new database: `CREATE DATABASE <database>;`
- Create a new user: `CREATE USER '<username>'@'localhost' IDENTIFIED BY '<password>';`
- Give it the permissions: `GRANT ALL PRIVILEGES ON <database>.* TO '<username>'@'localhost' WITH GRANT OPTION;`

The `init.sql` script then has to be executed as follows:

    mysql -u <username> -p <password> <database> < init.sql
