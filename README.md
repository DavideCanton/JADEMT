JADEMT
======

Message-template parsing library.

Example:

// (Performative = REQUEST) OR (Performative = INFORM AND Ontology = "House") OR Ontology = "Car"
MessageTemplate mt = MTBuilder.build("Performative = REQUEST | (Performative = INFORM & Ontology = House) | Ontology = Car");
