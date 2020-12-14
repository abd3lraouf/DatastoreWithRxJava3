#!/usr/bin/env bash
./gradlew uploadArchives --no-daemon --no-parallel
./gradlew closeAndReleaseRepository
