#!/bin/bash
(cd out/production/Rpc-rmi-dice-poker/ && java -Djava.rmi.dgc.leaseValue=3000 -Djava.security.policy=../../../grant-all.policy poker.game.ServerImpl)
