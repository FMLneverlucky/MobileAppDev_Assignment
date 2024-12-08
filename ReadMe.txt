yes this is still very incomplete
current features:
"flick" paper ball on screen (but it moves in wrong direction), flick refers to hold and release
that's it.

intended for paper ball to move in an axis according to flick direction vector but wanted to try out position updating first.
mostly spent my time cutting and pasting multitouch code in different classes (ended up in game scene class).
no wait i spent most of my time debugging the dumb render text on screen. debugging shows its within screen but its not shown. i cant debug using android studio for the life of me and i shall assume the text is buried under other bitmaps even though i deliberately placed it at the end of game scene constructor smh.

hey android studio fun, i can debug and peek at phone system events. too bad logcat dont have formatting like windows event listeners, its hard to keep track of the events