#!/usr/bin/env sh

sbt package
cp -v target/scala-2.11/*.jar website/website.jar
