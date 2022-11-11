# Portfolio Tracker

## An effective way to track portfolio value and return

As a *user* I'd like to be able to be able to:
- Track current portfolio value
- Input new transactions
- Update prices
- show whether expired on options
- Display metrics like percentage return
- Allows users to save portfolio
- Allows users to load portfolio




Users will be able to **maintain** and *track* themselves. 


##Phase 4: Task 2 Logging Application

My logging isn't working perfectly, I think this is the case because the string 
overwrites itself and I'm not sure why. I've stepped into it and seen that it does
pass through the data but for some reason it doesn't logged. 

##Phase 4: Task 3

What I'd refactor is that a lot of my wrapper calls are very forced 
and not intuitive, instead I'd like to make an abstract class which
handles all the wrapped calls. This abstract class would be extended by the
interface security for it cleanly changes to both sections. Would add a lot of
abstraction.