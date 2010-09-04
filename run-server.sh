#!/bin/bash
(cd out/production/Rpc-rmi-dice-poker/ && java -Djava.security.policy=../../../grant-all.policy poker.game.ServerImpl)
