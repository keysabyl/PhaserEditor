// The MIT License (MIT)
//
// Copyright (c) 2015, 2016 Arian Fornaris
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the
// "Software"), to deal in the Software without restriction, including
// without limitation the rights to use, copy, modify, merge, publish,
// distribute, sublicense, and/or sell copies of the Software, and to permit
// persons to whom the Software is furnished to do so, subject to the
// following conditions: The above copyright notice and this permission
// notice shall be included in all copies or substantial portions of the
// Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
// MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN
// NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
// USE OR OTHER DEALINGS IN THE SOFTWARE.
package phasereditor.canvas.core.codegen;

import phasereditor.canvas.core.AssetSpriteModel;
import phasereditor.canvas.core.CanvasModel;
import phasereditor.inspect.core.InspectCore;
import phasereditor.inspect.core.jsdoc.PhaserJSDoc;

/**
 * @author arian
 *
 */
public class JSSpriteCodeGenerator extends JSLikeBaseSpriteCodeGenerator {

	public JSSpriteCodeGenerator(CanvasModel model) {
		super(model);
	}

	@Override
	protected void generateHeader() {
		String classname = _settings.getClassName();
		String baseclass = _settings.getBaseClass();

		PhaserJSDoc help = InspectCore.getPhaserHelp();

		line("/**");
		line(" * " + classname + ".");
		line(" * @param {Phaser.Game} aGame " + help.getMethodArgHelp("Phaser.Sprite", "game"));
		line(" * @param {number} aX " + help.getMethodArgHelp("Phaser.Sprite", "x"));
		line(" * @param {number} aY " + help.getMethodArgHelp("Phaser.Sprite", "y"));
		line(" * @param {any} aKey " + help.getMethodArgHelp("Phaser.Sprite", "key"));
		line(" * @param {any} aFrame " + help.getMethodArgHelp("Phaser.Sprite", "frame"));
		line(" */");
		openIndent("function " + classname + "(aGame, aX, aY, aKey, aFrame) {");
		AssetSpriteModel<?> sprite = (AssetSpriteModel<?>) _model.getWorld().findFirstSprite();
		String key = "null";
		String frame = "null";
		if (sprite != null) {
			TextureArgs info = ICodeGenerator.getTextureArgs(sprite.getAssetKey());
			key = info.key;
			frame = info.frame;
		}
		line();
		line("var pKey = aKey === undefined? " + key + " : aKey;");
		line("var pFrame = aFrame === undefined? " + frame + " : aFrame;");
		line();
		line(baseclass + ".call(this, aGame, aX, aY, pKey, pFrame);");

		line();

		section(PRE_INIT_CODE_BEGIN, PRE_INIT_CODE_END, getYouCanInsertCodeHere());

		line();
		line();
	}
	

	@Override
	protected void generateFooter() {
		String classname = _settings.getClassName();
		String baseclass = _settings.getBaseClass();

		section(POST_INIT_CODE_BEGIN, POST_INIT_CODE_END, getYouCanInsertCodeHere());

		line();
		closeIndent("}");
		line();

		line("/** @type " + baseclass + " */");
		line("var " + classname + "_proto = Object.create(" + baseclass + ".prototype);");
		line(classname + ".prototype = " + classname + "_proto;");
		line(classname + ".prototype.constructor = " + classname + ";");
		line();

		section(END_GENERATED_CODE, getYouCanInsertCodeHere());
	}
}
