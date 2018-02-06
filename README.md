# Flames [![Clojars Project](https://img.shields.io/clojars/v/flames.svg)](http://clojars.org/flames)

Pluggable [Flame Graphs][fg] for Clojure.

[![Flames][png]][svg]

## Usage

It's easier than you think. Get the newest release. Require the `flames.core` namespace and start the profiler.

```clojure
(require '[flames.core :as flames])
(def flames (flames/start! {:port 54321, :host "localhost"}))
```

Wait 5 seconds and check out `http://localhost:54321/flames.svg`.
When you're done stop the profiler by invoking:

```clojure
(flames/stop! flames)
```

### Options

`flames.core/start!` accepts a map of following options:

  - `:host` – host to which the HTTP server will bind,
  - `:port` – port on which the HTTP server will listen,
  - `:dt` – number of seconds of profiling between updates of the flame graph,
  - `:load` – target fraction of one core's CPU time to use for profiling.

Find more information about `:dt` and `:load` options as well as principles of
operation of the profiler in [README of riemann-jvm-profiler][rjpreadme].

### Graph generation

Contents of the flame graph can be filtered using regular expressions passed
in `filter` and `remove` query params.

```
GET /flames.svg?remove=%28socketRead|epollWait|socketAccept|readBytes%29
GET /flames.svg?filter=flames.core
```

## Maturity status

Completely experimental.

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
[rjpreadme]: https://github.com/riemann/riemann-jvm-profiler/blob/0.1.0/README.md
