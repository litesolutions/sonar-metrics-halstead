#!/bin/bash

#
# This will build everything that is needed and push to Maven central.
#
# Note: we don't want the daemon for releases.
#
./gradlew --nodaemon --recompile-scripts clean test uploadArchives

