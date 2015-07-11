#!/usr/bin/env sh

sbt assembly
cp -v target/scala-2.11/*assembly*.jar website/website.jar
