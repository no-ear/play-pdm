/**
 * コレクションを表現するビュー
 * 
 * このクラスを使うときには必ず継承したクラスで、createSubViewの実装、collectionとelを指定すること。<br>
 * そういう意味でabstract、仮想関数持ちインターフェースクラスだと思ってちょうだい。
 * 
 * @class CollectionView
 */
var CollectionView = Backbone.View.extend(
/** @lends CollectionView# */
{
	/**
	 * インスタンス生成時に呼ばれます。
	 * 
	 * @param options
	 *            コンストラクタのoptionsへ渡した値です。
	 */
	initialize : function(options) {

		this.isAppend = false;

		// サブビューを保管するところ連想配列
		this.subViewArray = new Object();

		this.collection.each(function(model) {
			this.subViewArray[model.cid] = this.createSubView(model);
		}, this);

		this.listenTo(this.collection, 'reset', this.onReset);
		this.listenTo(this.collection, 'add', this.onAdd);
		this.listenTo(this.collection, 'remove', this.onRemove);
		this.listenTo(this.collection, 'destroy', this.onDestroy);
	},
	/**
	 * ビューを作成する関数。
	 * 
	 * @param model
	 *            作成要求のあったモデル
	 * @return サブビュー
	 */
	createSubView : function(model) {
		throw new Error("pure virtual function call");
	},
	/**
	 * リセットイベントコールバック関数.
	 * 
	 * @param collection
	 *            新しいコレクション
	 * @param options
	 *            オプション
	 */
	onReset : function(collection, options) {
		_.each(this.subViewArray, function(view) {
			view.remove();
		});

		// 連想配列クリア
		for ( var key in this.subViewArray) {
			delete this.subViewArray[key];
		}
		this.subViewArray.length = 0;

		collection.each(function(model) {
			this.subViewArray[model.cid] = this.createSubView(model);
		}, this);

		this.render();
	},
	/**
	 * 新モデル追加時コールバック関数.
	 * 
	 * @param model
	 *            新規追加モデル
	 * @param collection
	 *            コレクション
	 * @param options
	 *            オプション
	 */
	onAdd : function(model, collection, options) {
		var subView = this.createSubView(model);
		this.subViewArray[model.cid] = subView;

		var first = this.collection.first();
		if (first.cid == model.cid) {
			if (this.collection.length >= 2) {
				// 他に要素が存在する時はそれにくっつける。
				var nextModel = this.collection.at(1);
				this.subViewArray[nextModel.cid].$el.before(subView.render().el);
			} else {
				// appendかprependかはビューによって異なる。
				// あまりないことだけど要素の同階層に他の要素があった場合の挙動に関わる。
				// デフォルトでは他の管理外の要素は上に出る。Tableの場合thタグがあるのでこれが正しいような気がする。
				if (this.isAppend) {
					this.$el.append(subView.render().el);
				} else {
					this.$el.prepend(subView.render().el);
				}
			}
		} else {
			var prevModel = this.collection.at(0);

			for (var i = 1; i < this.collection.length; i++) {
				var checkModel = this.collection.at(i);
				if (model.cid == checkModel.cid) {
					break;
				}

				prevModel = checkModel;
			}

			this.subViewArray[prevModel.cid].$el.after(subView.render().el);
		}
	},
	/**
	 * モデル破棄時コールバック関数.
	 * 
	 * @param model
	 *            破棄モデル
	 * @param collection
	 *            コレクション
	 * @param options
	 *            オプション
	 */
	onRemove : function(model, collection, options) {
		this.subViewArray[model.cid].remove();

		delete this.subViewArray[model.cid];
	},
	/**
	 * モデル破棄時コールバック関数.
	 * 
	 * @param model
	 *            破棄モデル
	 * @param collection
	 *            コレクション
	 * @param options
	 *            オプション
	 */
	onDestroy : function(model, collection, options) {
		this.subViewArray[model.cid].remove();

		delete this.subViewArray[model.cid];
	},
	/**
	 * elプロパティに設定されているDOM要素をルートとするDOMツリーに対する操作を記述するメソッドです。
	 * 
	 * @return thisを返却して、呼び元でメソッドチェーンを使えるようにする
	 */
	render : function() {
		var $html = $("<div>");

		this.collection.each(function(model) {
			// 大量に追加したときとかにコレクションの数とサブビューの数が違うことがあるので。
			if (typeof this.subViewArray[model.cid] != "undefined") {
				$html.append(this.subViewArray[model.cid].render().el);
			}
		}, this);

		this.$el.html($html.children());

		return this;
	}
});
