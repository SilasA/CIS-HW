import unittest
from DFA import DFA

class TestDFA(unittest.TestCase):
    def __init__(self, *args, **kwargs):
        super(TestDFA, self).__init__(*args, **kwargs)
        # Test state machine is a machine that accepts an even
        # number of the sequence "105102"
        self.dfa = DFA()
        self.dfa.start_state = "0"
        self.dfa.states = {
            "0":True, "1":True, "2":False, "3":False
        }
        self.dfa.transform_table = {
            "0": { "105": "1", "*": "0" },
            "1": { "105": "1", "102": "2", "*": "0" },
            "2": { "105": "3", "*": "2" },
            "3": { "105": "3", "102": "0", "*": "2" }
        }
        self.dfa.reset()
        return

    def test_transform(self):
        return

    def test_process(self):
        return

    def test_reset(self):
        return

    def test_is_accepted(self):
        strings = ['',
                '105102105102',
                '119104105108101',
                '119104111293999105102003',
                '345747938105102539382732123094382924105102119104105108101',
                '123947193476593485774539',
                '109']
        accepted = [True, True, True, False, True, True, True]
        for i in range(0, len(strings)):
            self.dfa.reset()
            self.assertEqual(self.dfa.is_accepted(strings[i]), accepted[i])
        return

if __name__ == '__main__':
    unittest.main()
