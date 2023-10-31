#!/bin/bash

# Iniciar Supervisor para gestionar Nginx y la aplicación Java
/usr/bin/supervisord -c /etc/supervisor/supervisord.conf
