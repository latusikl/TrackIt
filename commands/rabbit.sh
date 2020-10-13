#!/bin/bash
docker run -d --hostname my-rabbit --name trackItRabbit -p 15672:8000 5672:5672
