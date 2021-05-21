# Battleship-Game

Watch the demo here: https://youtu.be/X4T7q9Hp8mI

Our battleship goes beyond expectations and it incorporates input validation, a intelligent cpu, efficient ways to minimize lines and minimize compiling time, it stops the user and cpu from attacking the same spot twice, it utilises arrays to their full potential.
The CPU:The chrisBot cpu (made by christopher suh) was designed to be able to challenge the user and improve the quality of the game. Our cpu doesnt just take the positions of the 
player's boat from the array. The cpu is given the same information as the user and makes its decisions based on that information. In mode one the cpu attacks randomly, once the cpu gets a hit it goes into mode 2 where it attacks the coordinate to the right, then left, then down, then up. If it gets a hit it will keep attacking in that direction, if it keeps going in that direction then gets a miss then it will proceed to the next direction (ex: attacking right misses, goes left now). It will keep going until it gets a notification that a player ship has been sunken. I also had to fix that the bot could attack coordinates outside the grid (crashing the program). The bot has been throughly checked for any possible errors (there were many to fix) and is working perfectly. It can rival any human player.
