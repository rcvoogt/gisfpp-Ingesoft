CKEDITOR.editorConfig = function( config ) {
	config.toolbar = [
	          		{ name: 'clipboard', items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
	          		{ name: 'editing', items: [ 'Scayt' ] },
	          		{ name: 'links', items: [ 'Link', 'Unlink' ] },
	          		{ name: 'insert', items: [ 'Table', 'HorizontalRule', 'SpecialChar' ] },
	          		'/',
	          		{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
	          		{ name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote' ] },
	          		{ name: 'styles', items: [ 'Styles', 'Format' ] },
	  ];
	config.toolbarCanCollapse = true;
	config.readOnly = true;
	config.removePlugins = 'elementspath'; 
};