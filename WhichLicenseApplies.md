#XDocReport code is license under **MIT license** but the samples are licensed under **LGPL license**, this is why.

# XDocReport Core #

Because we love to share our code, we prefer a very weak license. That's why we choose **MIT License** for our core modules.

MIT License is called a weak license which allow redistribution and modification (Although we appreciate contributions).


# Issue with iText based PDF converter #

iText is a very powerful Java library for creating PDF. Our iText based converter is by far the best PDF converter that we provide.
iText 2.1.7 comes with a **LGPL license** which is not compliant with our **MIT License**.

Thus, people that would use and redistribute xdocreport with iText based converter will have to comply with the **LGPL license**.

This is exactly what we do with our samples :
https://code.google.com/p/xdocreport/source/browse/?repo=samples

Which are licensed under **LGPL license** and not the **MIT License**.


**Users must be aware that we use an "old" version of iText that is not supported anymore by its authors for both technical and legal reasons.**

Authors of iText (http://itextpdf.com/), provide now a supported version under AGPL license. There is no XDocReport Converter for this version.