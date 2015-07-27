![rv-adapter-endless on Travis CI](https://travis-ci.org/rockerhieu/rv-adapter-endless.png?branch=master) ![rv-adapter-endless on Maven Central](https://maven-badges.herokuapp.com/maven-central/com.rockerhieu/rv-adapter-endless/badge.svg)

## rv-adapter-endless

`EndlessRecyclerViewAdapter` support for RecyclerView.Adapter

## Installation

```groovy
compile 'com.rockerhieu:rv-adapter-endless:<latest-version>'
```

## Usage

To use `EndlessRecyclerViewAdapter` you need to create a subclass that will control the endlessness, specifying what `View` to use for the loading placeholder.

When the loading view is shown, it will send a request to load more data via the interface `RequestToLoadMoreListener`. After loading the data, you can let the adapter know via `onDataReady()` method.

```java
OriginalAdapter adapter = new OriginalAdapter();
EndlessRecyclerViewAdapter endlessRecyclerViewAdapter = new EndlessRecyclerViewAdapter(this, adapter, new RequestToLoadMoreListener() {
   @Override
   public void onLoadMoreRequested() {
      loadMoreData(new OnSuccess() {
        void onSuccess(List<Item> items) {
          adapter.append(items);
          // notify that the data is ready, and the adapter SHOULD continue to load more
          endlessRecyclerViewAdapter.onDataReady(true);
        }
      }, new OnError() {
        void onError() {
          // notify that the data is ready, and the adapter SHOULD NOT continue to load more
          endlessRecyclerViewAdapter.onDataReady(false);
        }
      )
   }
});
rv.setAdapter(endlessRecyclerViewAdapter);
```

You can have a look at the example project for more details.

## Contributing

Please fork this repository and contribute back using
[pull requests](https://github.com/rockerhieu/rv-adapter-endless/pulls).

Any contributions, large or small, major features, bug fixes, additional
language translations, unit/integration tests are welcomed and appreciated
but will be thoroughly reviewed and discussed.

## License

```
The MIT License (MIT)

Copyright (c) 2015 Hieu Rocker

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
