
function ConvertFile() {
  const [file] = document.getElementById('in_file').files;
  const reader = new FileReader();

  reader.addEventListener("load", () => {
	ParseFile(file.name, reader.result);
  }, false);

  if (file) {
    reader.readAsText(file);
  }
}

function ParseFile(file_name, data) {

	let json = JSON.parse(data);
	let curr_name = '';
	let bStart = true;
	let strPath = "";
	let strHtml = "";
	let strHtmlCvs = "";
	let maxX = 0;
	let maxY = 0;
	let ar_names = [];

	for (pic_name in json) {
		if ("__jstorage_meta" == pic_name) continue;

		let pure_name = pic_name.substr(0, pic_name.indexOf('_'));

		if (pure_name != curr_name) {

			if (bStart) {
				bStart = false;
			} else {
				// write previous
				strHtml += '</svg>';
				strHtml += '<svg  xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="' + curr_name + '" width="' + (maxX + 100) + '" height="' + (maxY + 100) + '">';
				strHtml += strPath;
				strHtmlCvs += '<canvas id="canvas_' + curr_name + '" width="' + (maxX + 100) + '" height="' + (maxY + 100) + '"></canvas>';
				strPath = "";
				maxX = 0;
				maxY = 0;
			}

			curr_name = pure_name;
			ar_names.push(pure_name);

			// write new
			strHtml += '<h3>' + pure_name + '</h3>';
		}
	
		let paths = json[pic_name];
		for (let i = 0; i < paths.length; i++) {
			if (paths[i].type != "stroke") {
				console.error("type is not stroke - pic_name: ", pic_name, " type: ", paths[i].type);
			} else if (paths[i].x.length != paths[i].y.length) {
				console.error("lengths do not match - pic_name: ", pic_name, " x len: ", paths[i].x.length, " y len: ", paths[i].y.length);
			} else {
				strPath += '<path fill="none" stroke="black" d="M';
				for (let j = 0; j < paths[i].x.length; j++) {
					if (j != 0) {
						strPath += ' L'
					}
					strPath += ' ' + paths[i].x[j] + ' ' + paths[i].y[j];
					if (paths[i].x[j] > maxX) {
						maxX = paths[i].x[j];
					}
					if (paths[i].y[j] > maxY) {
						maxY = paths[i].y[j];
					}
				}
				strPath += '" />';
			}
		}
	}
	strHtml += '</svg>';
	strHtml += '<svg  xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" id="' + curr_name + '" width="' + (maxX + 100) + '" height="' + (maxY + 100) + '">';
	strHtml += strPath;
	strHtmlCvs += '<canvas id="canvas_' + curr_name + '" width="' + (maxX + 100) + '" height="' + (maxY + 100) + '"></canvas>';

	// convert to jpg
	document.getElementById('svgs').innerHTML = strHtml;
	document.getElementById('canvases').innerHTML = strHtmlCvs;
	for (let name of ar_names) {
		Convert2jpg(name);
	}
}

function triggerDownload (imgURI, name) {
  var evt = new MouseEvent('click', {
    view: window,
    bubbles: false,
    cancelable: true
  });

  var a = document.createElement('a');
  a.setAttribute('download', name + '.jpeg');
  a.setAttribute('href', imgURI);
  a.setAttribute('target', '_blank');

  a.dispatchEvent(evt);
}

function Convert2jpg(name) {
  var canvas = document.getElementById('canvas_' + name);
  var svg = document.getElementById(name);
  var context = canvas.getContext('2d');
  context.fillStyle = 'white';
  context.fillRect(0, 0, canvas.width, canvas.height);
  var data = (new XMLSerializer()).serializeToString(svg);
  var DOMURL = window.URL || window.webkitURL || window;

  var img = new Image();
  var svgBlob = new Blob([data], {type: 'image/svg+xml;charset=utf-8'});
  var url = DOMURL.createObjectURL(svgBlob);

  img.onload = function () {
    context.drawImage(img, 0, 0);
    DOMURL.revokeObjectURL(url);

    var imgURI = canvas
        .toDataURL('image/jpeg', 1)
        .replace('image/jpeg', 'image/octet-stream');

    triggerDownload(imgURI, name);
  };

  img.src = url;
}
