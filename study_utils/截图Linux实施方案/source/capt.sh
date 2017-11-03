#!/bin/sh
/opt/CutyCapt/xvfb-run.sh --server-args="-screen 0, 1280x1600x24" /opt/CutyCapt/CutyCapt --url=$1 --out=$2
