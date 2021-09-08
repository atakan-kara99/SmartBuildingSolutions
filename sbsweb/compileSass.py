# Very basci script to compile custon style SASS files to be used with bootstrap

import os

print("Compiling boostrap themes with SASS")
os.system("sass src/main/resources/scss/custom/sbs_bootstrap_light.scss src/main/resources/scss/custom/sbs_bootstrap_light.css")
os.system("sass src/main/resources/scss/custom/sbs_bootstrap_dark.scss src/main/resources/scss/custom/sbs_bootstrap_dark.css")
print("Moving bootsrap themes")
os.system("mv src/main/resources/scss/custom/sbs_bootstrap_light.css src/main/resources/static/css/sbs_bootstrap_light.css")
os.system("mv src/main/resources/scss/custom/sbs_bootstrap_light.css.map src/main/resources/static/css/sbs_bootstrap_light.css.map")
os.system("mv src/main/resources/scss/custom/sbs_bootstrap_dark.css src/main/resources/static/css/sbs_bootstrap_dark.css")
os.system("mv src/main/resources/scss/custom/sbs_bootstrap_dark.css.map src/main/resources/static/css/sbs_bootstrap_dark.css.map")
print("Done")