# ChessEngine

This is a personal project I've been doing in my free time, trying to create the infrastructure for a simple chess engine, and possibly add updated functionality and improved AI down the line.

## Construction

The program makes uses of a couple design patterns, namely the singleton pattern which is used for the Gameboard and a comprehensive history of moves and (relatively) thought out abstraction.

## Gameplay

The UI and UX is currently very rough and only done through ASCII StdIn/StdOut as I focus on the backend and construction of the engine, but I may update it in time (currently planning on using JavaScript and React for the front end with Java as the backend).

## AI

The current AI uses simulated rollouts to determine moves, with difficulty scaling based on number of simulations, but currently has bugs and is being worked on.