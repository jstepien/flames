# Flames

Pluggable [Flame Graphs][fg] for Clojure.

[![Flames][png]][svg]

## Usage

```clojure
(flames.core/start! {:port 54321, :host "localhost"})
```

Wait 5 seconds and check out `http://localhost:54321/flames.svg`.

## Maturity status

Surely you're joking.

## License

Copyright © 2015 Jan Stępień

Permission is hereby granted, free of charge, to any person
obtaining a copy of this software and associated documentation
files (the "Software"), to deal in the Software without
restriction, including without limitation the rights to use,
copy, modify, merge, publish, distribute, sublicense, and/or
sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.

[fg]: http://www.brendangregg.com/flamegraphs.html
[png]: https://stepien.cc/~jan/flames-01.png
[svg]: https://stepien.cc/~jan/flames-01.svg
