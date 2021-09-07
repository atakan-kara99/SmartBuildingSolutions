import os

print("Compiling boostrap theme with SASS")
os.system("sass src/main/resources/scss/custom/sbs_bootstrap.scss src/main/resources/scss/custom/sbs_bootstrap.css")
print("Moving bootsrap theme")
os.system("mv src/main/resources/scss/custom/sbs_bootstrap.css src/main/resources/static/css/sbs_bootstrap.css")
print("Done")