# wakatakakage
A small utility library of generic joins between Scala maps. Written after a search for an existing
outer join implementation yielded only suggestions to use Spark which is just massively overkill for
the small in-memory use cases I was working on. The outer join is the only remotely complex piece of
code here but I've needed these things often enough that it's probably just better to have a library
out there rather than having people need to waste brainpower reinventing an ancient wheel every time
they need to join some maps. Slàinte mhath!
