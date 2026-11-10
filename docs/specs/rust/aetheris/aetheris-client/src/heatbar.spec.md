# aetheris-client::heatbar

Component file. It carries how this package is built.

**Depends:** This container names no other spec.

## The heatbar beat pulses on both tabs

The heat-bar beat pulses when the count changes, on the VT100 tab and on the canvas tab, the way the Swing composer beat already pulses.

The arm frame draws the 6 pixel lunge and does not ease it. A later frame eases one step, the lunge times 0.72, when 16 ms have passed, and asks for another frame in 16 ms. A faster frame does not spend that step. A long gap spends one step. The flash eases on that same step. The beat is stored on one fixed id. The 5 pixel track stays. The 2 by 9 needle stays. The posts stay. The inks stay. A held Roux count still replaces the glass.

The beat stays in paint and in the step. The Rust client changes. The Swing client stays. The wire does not gain, lose, or reorder a system line.

**Truth.** On both tabs, a count change arms a 6 pixel lunge with no ease on that frame. Each later frame, after 16 ms, eases one step by multiplying the remaining lunge by 0.72 and schedules the next frame 16 ms out. A frame sooner than 16 ms does not step. A longer gap still steps once. The flash eases on that step. One fixed id holds the beat. A held Roux count still replaces the glass estimate. The track stays 5 pixels, the needle 2 by 9, and the posts and inks stay.

**Reason.** The Rust needle jumped to the new edge. The Swing beat already lunges 6 pixels and eases back, and the Rust tabs did not.

**Avoidances.** Do not ease on the arm frame. Do not step more than once per 16 ms. Do not spend the step on a faster frame. Do not spend more than one step across a long gap. Do not ease the flash on a different clock. Do not use a second id for the beat. Do not let the pulse replace a held Roux count. Do not change the 5 pixel track, the 2 by 9 needle, the posts, or the inks. Do not change the Swing client. Do not add a system line on the wire. Do not add a package, a public type, a public method, a private method, a shortcut, a stored column, a channel, a wire type, a color key, or a library. Do not edit the Rust manifest.


**semantic-key:** PK-4484 PK-4485

# The heatbar beat pulses when the count changes

The full cycle is named, and the build is named. Rust moves. Java stays. Where this page is thinner than the locked row, the locked row wins.


**semantic-key:** PK-4499 PK-4500

# The heatbar beat pulses when the count changes

The pre-plan is locked, and a second revision is not opened. The full cycle is named, and the build is named, in the slice sentence itself. Rust is named. Java stays. The seats for the work are already filed on the locked row, and no extra cut is opened. Where this page is thinner than the locked row, the locked row wins. The formal plan is stamped from this page. Dependencies were filed before the landing was filed on the plan. The page carries a new-item mark.


**semantic-key:** PK-4571 PK-4572
