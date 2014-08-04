function setMonthSelect(selectId) {

			var currentMonth = new Date().getMonth() + 1;	// getMonth是从0-11
			
			for (var i=1; i<=12; i++) {
				var anOption = document.createElement("option");
				anOption.text = i + "月";
				anOption.value = i;
				document.getElementById(selectId).options.add(anOption);

				if (i == currentMonth) {
					anOption.selected = "selected";
				}
			}
		}