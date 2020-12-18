<h1 class="code-line" data-line-start=0 data-line-end=1 ><a id="Busbot_0"></a>Busbot</h1>
<h3 class="code-line" data-line-start=2 data-line-end=3 ><a id="What_is_this_2"></a>What is this?</h3>
<p class="has-line-data" data-line-start="3" data-line-end="4">Basically these are some selenium webdriver scripts to help me purchase a Playstation 5. I wanted to get back into some sort of development as I am getting really rusty. Welp, I hope you enjoy!</p>
<h3 class="code-line" data-line-start=5 data-line-end=6 ><a id="Prerequisits_5"></a>Prerequisits</h3>
<ol>
<li class="has-line-data" data-line-start="6" data-line-end="7">Download Java. Verify by opening a command prompt and typing</li>
</ol>
<pre><code class="has-line-data" data-line-start="8" data-line-end="10" class="language-sh">java -version
</code></pre>
<p class="has-line-data" data-line-start="10" data-line-end="11">You should see something like this.</p>
<pre><code class="has-line-data" data-line-start="12" data-line-end="15" class="language-sh"> -     java version <span class="hljs-string">"1.8.0_271"</span>
 -     Java(TM) SE Runtime Environment (build <span class="hljs-number">1.8</span>.<span class="hljs-number">0</span>_271-b09)
</code></pre>
<ol start="2">
<li class="has-line-data" data-line-start="15" data-line-end="17">Download Eclipse (<a href="https://www.eclipse.org/downloads/">https://www.eclipse.org/downloads/</a>)</li>
</ol>
<h3 class="code-line" data-line-start=17 data-line-end=18 ><a id="Setup_17"></a>Setup</h3>
<ol>
<li class="has-line-data" data-line-start="18" data-line-end="19">Open Eclipse -&gt; ‘File’ -&gt; ‘Open Projects From Filesystem…’.</li>
<li class="has-line-data" data-line-start="19" data-line-end="20">Click ‘Directory’ and select the folder of this project. Click ‘Finish’.</li>
<li class="has-line-data" data-line-start="20" data-line-end="21">Configure your build path (google it). All libraries needed to run are in the ‘lib’ folder. (Basically update the correct location of the libraries).</li>
<li class="has-line-data" data-line-start="21" data-line-end="22">Copy the ‘keys’ file and paste it in the same directory. Rename it to ‘mykeys’.</li>
<li class="has-line-data" data-line-start="22" data-line-end="24">Fill out the values for all of the keys on the ‘mykeys’ file &gt; Save.</li>
</ol>
<h3 class="code-line" data-line-start=24 data-line-end=25 ><a id="How_To_Run_Your_First_Bot_24"></a>How To Run Your First Bot</h3>
<ol>
<li class="has-line-data" data-line-start="25" data-line-end="26">In eclipse, go to ‘src’ &gt; ‘sites’ and double click on the  store your’e interested in. The class will display in the center pane.</li>
<li class="has-line-data" data-line-start="26" data-line-end="27">Reveiw the code. It should be real easy to read.</li>
<li class="has-line-data" data-line-start="27" data-line-end="28">Right click anywhere in the center pane and select ‘Run As’ &gt; ‘Java Application’.</li>
<li class="has-line-data" data-line-start="28" data-line-end="29">If set up correctly, you should see chrome pop up and run the script!</li>
<li class="has-line-data" data-line-start="29" data-line-end="30">Some of the stores are quirky, feel free to fix/update my code :)</li>
<li class="has-line-data" data-line-start="30" data-line-end="31">Good luck!</li>
</ol>
