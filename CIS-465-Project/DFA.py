# !/usr/bin python3
# DFA implementation
###########################################################
# Alphabet is {000,001,002,...,998,999}
# Represented by a map of states and whether or not they
#  are an accept state in self.states.
#  The start state is also declared in self.start_state
# Transitions are represented in self.transform_table as a
#  nested map of current state then the current processed
#  letter.  this will retrieve the next state.
# Current state is stored in self.current_state
### Functions #############################################
# transform - returns the next state based on the input
#  letter.
# process - process a letter in the machine based on the
#  current state of the machine and the input letter
# reset - reset the machine state to the beginning.
# is_accepted - determines if a string is accepted by the
#  machine.  This handles the entire process of the machine
###########################################################

class DFA:
    def __init__(self):
        self.current_state = -1
        self.start_state = "A0B0"
        self.states = {
            "A0B0": False, "A1B0": False, "A2B0": False, "A3B0": False,
            "A0B1": False, "A0B2": False, "A0B4": False, "A0B5": True,
            "A0B6": True, "A0B7": True, "A0B9": True, "A1B3": False,
            "A1B5": True, "A1B8": True, "A2B1": False, "A2B2": False,
            "A2B4": False, "A2B5": False, "A2B6": False, "A2B7": False,
            "A2B7": False, "A2B9": False, "A3B3": False, "A3B5": False,
            "A3B8": False
        }
        self.transform_table = {
            "A0B0": { "105": "A1B0", "119": "A0B1", "*": "A0B0" },
            "A1B0": { "105": "A1B0", "119": "A0B1", "102": "A2B0", "*": "A0B0" },
            "A2B0": { "105": "A3B0", "119": "A2B1", "*": "A2B0" },
            "A3B0": { "105": "A3B0", "119": "A2B1", "102": "A0B0", "*": "A2B0" },

            "A0B1": { "105": "A1B0", "119": "A0B1", "104": "A0B2", "*": "A0B0" },
            "A0B2": { "105": "A1B3", "119": "A0B1", "*": "A0B0" },
            "A0B4": { "105": "A1B0", "119": "A0B1", "101": "A0B5", "*": "A0B0" },
            "A0B5": { "105": "A1B5", "119": "A0B6", "*": "A0B5" },
            "A0B6": { "105": "A1B5", "119": "A0B6", "104": "A0B7", "*": "A0B5" },
            "A0B7": { "105": "A1B8", "119": "A0B6", "*": "A0B5" },
            "A0B9": { "105": "A1B5", "119": "A0B6", "101": "A0B0", "*": "A0B5" },

            "A1B3": { "105": "A1B0", "102": "A2B0", "119": "A0B1", "108": "A0B4", "*": "A0B0" },
            "A1B5": { "105": "A1B5", "102": "A2B5", "119": "A0B6", "*": "A0B5" },
            "A1B8": { "105": "A1B5", "102": "A2B5", "108": "A0B9", "119": "A0B6", "*": "A0B5" },

            "A2B1": { "105": "A3B0", "119": "A2B1", "104": "A2B2", "*": "A2B0" },
            "A2B2": { "105": "A3B3", "119": "A2B1", "*": "A2B0" },
            "A2B4": { "105": "A3B0", "119": "A2B1", "101": "A2B5", "*": "A2B0" },
            "A2B5": { "105": "A3B5", "119": "A2B6", "*": "A2B5" },
            "A2B6": { "105": "A3B5", "119": "A2B6", "104": "A2B7", "*": "A2B5" },
            "A2B7": { "105": "A3B8", "119": "A2B6", "*": "A2B5" },
            "A2B9": { "105": "A3B5", "119": "A2B6", "101": "A2B0", "*": "A2B5" },

            "A3B3": { "105": "A3B0", "102": "A0B0", "119": "A2B1", "108": "A2B4", "*": "A2B0" },
            "A3B5": { "105": "A3B5", "119": "A2B6", "102": "A0B5", "*": "A2B5" },
            "A3B8": { "105": "A3B5", "119": "A2B6", "102": "A0B5", "108": "A2B9", "*": "A2B5" }
        }
        return

    def transform(self, letter):
        if (letter in self.transform_table[self.current_state].keys()):
            return self.transform_table[self.current_state][letter]
        else:
            return self.transform_table[self.current_state]["*"]

    def process(self, letter):
        self.current_state = self.transform(letter)
        return

    def reset(self):
        self.current_state = self.start_state
        return

    def is_accepted(self, string):
        self.reset()
        letters = [string[i:i + 3] for i in range(0, len(string), 3)]
        print("States:\n" + str(self.current_state), end="")
        for letter in letters:
            self.process(letter)
            print(", " + str(self.current_state), end="")
        print("")
        return self.states[self.current_state]
