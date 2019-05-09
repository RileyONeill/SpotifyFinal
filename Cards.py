class Card:
    def __init__(self, rank, suit):
        self.rank = rank
        self.suit = suit

        if self.rank == 'Ace':
            self.value = 11
        elif self.rank == 'Jack' or self.rank == 'Queen' or self.rank == 'King':
            self.value = 10
        else:
            self.value = int(self.rank)

    def __repr__(self):
        return "<Test value:%s suit:%s>" % (self.rank, self.suit)

    def __str__(self):
        return "%s of %s" % (self.rank, self.suit)

    def get_value(self):
        return self.value


