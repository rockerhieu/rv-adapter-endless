## Contribution
I needed to customize pendingViewHolder -because i use StaggeredGridLayoutManager and i need pendingViews to use fullSpan so in viewHolder i do so. In order to have this happened, i need to create my own view then pass it to the library, so i made that part little bit more flexible.

And also realised that it should not call notifyDataSetChanged everytime, since we have the ability to notify specific portions now. So i made it optional, up to flag.

Finally, since we receive parent object from adapter when it calls onCreateViewHolder, we don't actually need to pass context into this adapter just to create pendingView.

[<b>Original ReadMe</b>] [1]

[1]: https://github.com/rockerhieu/rv-adapter-endless/blob/master/README.md
