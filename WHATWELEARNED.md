What we have learned from this project
======================================

Scala
-----

We had never used a programing language such as Scala before. We had done some
OCaml during the first year of our studies but it stayed at a very basic level
and the goal was to understand basic programming before going deep into object
oriented or functional programming. Therefore this (small) first impression has
helped us put together and combine for the first time these two notions. We
sometimes have bad habits left over from our Java experience and try to force
ourselves to harness the power of Scala. Time will be our friend. We are
enjoying Scala a lot and are impressed by the amount of things we can do in just
one or two lines of code.

### Image to ASCII converter

Started with this part to get an understanding of the syntax of Scala. A lot of
what was done was similar to Java.

### Multipart form parser

A lot of string manipulation and documentation reading was done for this. We
encountered a big problem when we started to read Stdin as bytes when we wanted
to save a file. This problem enabled us to read a lot of Scala documentation,
how to work with Java when needed and the small yet **very** important
differences these two languages have from time to time.

### Reflection

A little reflection was done using the `scala-reflect` library. The goal was to
reproduce a similar behavior to Spring MVC in terms of route matching (without
the nested variables in the URL).

SBT
---

When I (Yoann) discovered SBT, I had a little trouble configuring it for our IDE
(Intellij Idea). But once it was done, I must say that its features really are
powerful. Just being able to add dependencies/plugins and letting it do all the
work really is pleasant.

Slick
-----

### Hangman

This was the first time I (Elliot) used an ORM. It was cool and fun to see how
simply queries can be accomplished with Slick! This part of the project was very
appreciated by both of us because we started to feel comfortable with the
environment and the tools we are using.

### Installation

Slick was surprinsingly easy to install and configure. The Typesafe's docs are
complete and easy to follow. And creating tables is very intuitive.
