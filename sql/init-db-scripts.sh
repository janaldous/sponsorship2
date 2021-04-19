#!/bin/bash
set PGPASSWORD=password

echo "creating sponsorship db"

psql -U postgres postgres < create-db.sql || exit 1

echo "created sponsorship db"

echo "filling sponsorship db"

psql -U postgres sponsorship < sponsorship.sql || exit 1

echo "filled sponsorship db"