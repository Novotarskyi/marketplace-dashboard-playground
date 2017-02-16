A small playground for an exchange marketplace, built with pure Java SDK

BUY's are ordered from top to bottom by price
SELL's are order bottom to top by price.

Supports an order ledger and rebuilding the live order dashboard based on the ledger.

Autotrading matching buys and sells is a very simple operation to add

Complexities:
Managing the ledger is amortized constant for write and O(1) for read

Placing an order aswell as removing an order (keeping the said order) is O(log(N))

Map seemed an appropriate datastore (since we order, sum and subtract by price, which is unique)

Used a TreeMap to help manage the custom ordering via O(log(N)) complexity (since it's a red-black tree under the hood)


Other thoughts:
The approach used is a write-through-cache appoach where any ledger activity brings a reaction to the dashboard

In cases where ledger is a reporting mechanism, we could use a read-through-cache with reaction events going to the ledger as a sideffect

This is a more usual mechanism when autotrading is implemented and order fulfillment performance is the key KPI.
