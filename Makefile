
.SUFFIXES: .class .java

JAVAC 		= javac
FLAGS		=
CLASSPATH	= ./src/
OUTDIR		= out/production/Rpc-rmi-dice-poker/
SRCS		= ./src/poker/game/Game.java \
	./src/poker/game/GameParticipant.java \
	./src/poker/game/GameParticipantImpl.java \
	./src/poker/game/GameState.java \
	./src/poker/game/Server.java \
	./src/poker/game/ServerImpl.java \
	./src/poker/game/GameImpl.java \
	./src/poker/game/resultTesters/ResultTester.java \
	./src/poker/game/resultTesters/NothingTester.java \
	./src/poker/game/resultTesters/TwoPairsTester.java \
	./src/poker/game/resultTesters/TripleTester.java \
	./src/poker/game/resultTesters/SmallStritTester.java \
	./src/poker/game/resultTesters/BigStritTester.java \
	./src/poker/game/resultTesters/FullTester.java \
	./src/poker/game/resultTesters/FourTester.java \
	./src/poker/game/resultTesters/PokerTester.java \
	./src/poker/game/resultTesters/PairTester.java \
	./src/poker/gui/PokerUI.java \
	./src/poker/Starter.java

OBJS		= ./src/poker/game/Game.class \
	./src/poker/game/GameParticipant.class \
	./src/poker/game/GameParticipantImpl.class \
	./src/poker/game/GameState.class \
	./src/poker/game/Server.class \
	./src/poker/game/ServerImpl.class \
	./src/poker/game/GameImpl.class \
	./src/poker/game/resultTesters/ResultTester.class \
	./src/poker/game/resultTesters/NothingTester.class \
	./src/poker/game/resultTesters/TwoPairsTester.class \
	./src/poker/game/resultTesters/TripleTester.class \
	./src/poker/game/resultTesters/SmallStritTester.class \
	./src/poker/game/resultTesters/BigStritTester.class \
	./src/poker/game/resultTesters/FullTester.class \
	./src/poker/game/resultTesters/FourTester.class \
	./src/poker/game/resultTesters/PokerTester.class \
	./src/poker/game/resultTesters/PairTester.class \
	./src/poker/gui/PokerUI.class \
	./src/poker/Starter.class


.java.class: $@
	$(JAVAC) $(FLAGS) -cp $(CLASSPATH) -d $(OUTDIR) $<


all: $(OBJS)
