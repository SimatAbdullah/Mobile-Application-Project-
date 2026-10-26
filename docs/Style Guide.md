Loopit Style Guide

This style guide records the design decisions from A2 Design Foundations (Style Guide) in the CSC 402 Milestone 2 report. The values below preserve the decisions stated in the report
Colors
Hex Code
	Category | Color Name 	Used for

#504DCC
	Primary color | Blue-violet
	Primary buttons, links, selected navigation items, and recycling icon. 

#2382BA
	Accent color | Blue
	Collection-point icons and challenge progress; stars and points highlights. 

#DF911A
	Rewards | Gold
	
#504DCC
	Status colors | Success
	pending: neutral gray Recycling confirmation, validation errors, and disabled or locked states. Status is also communicated through icons and text.

#D73431
	a checkmark; error
	

Typography
Type
	Font size
	Used for

         Screen titles
	         18px bold
	Main screen headlines and TopAppBar titles.
section headings
	           16–20px bold
	Card headers, category labels, and section titles.
body
 	16px
	Primary paragraph text, instructions, and checklist items.


Labels & Subtitles	12–14px	Secondary descriptions, timestamps, and input labels.
points total	36px extra-bold	Points summary on Rewards & Profile screens.

Planned Material 3 UI Components
The UI implementation maps directly to the following Google Material 3 Jetpack Compose components:
1. TopAppBar : Used for screen headers with back navigation arrows.
 2. NavigationBar & NavigationBarItem : Persistent bottom navigation bar for core screens (Home, Identify, Points, Rewards, Profile). 
3. Button & OutlinedButton: Primary action buttons (pill-shaped) and secondary outlined buttons.
 4. Card  & OutlinedCard : Container units for statistics, collection points, and history entries.
 5. Checkbox : Interactive checklist items on the Pre-Disposal Checklist screen.
 6. LinearProgressIndicator: Progress visualizer for checklists and challenge completion.
 7.  Switch : Toggle switches on the Notifications screen.
