% References
% Elements used from previous semester's project
% https://github.com/roccovulpis/Probability-and-Applied-Stats-Project-2/blob/f54ca2eddf404608e995c81af324c2688d1f2072/PlottingSaltingSmoothing/Octave/Code/plotting_salting_smoothing.m
% Creating multiple plot windows.
% https://docs.octave.org/v4.4.0/Multiple-Plot-Windows.html
% Setting the size and positions of plot windows.
% https://lists.gnu.org/archive/html/help-octave/2009-08/msg00184.html
% Generate random numbers.
% https://docs.octave.org/v6.4.0/Random-Number-Generation.html
% Conditional statements in Octave.
% https://docs.octave.org/latest/The-if-Statement.html
% Calculating the moving average over a sliding window.
% https://docs.octave.org/v6.3.0/Statistics-on-Sliding-Windows-of-Data.html
% Linearspace
% https://octave.sourceforge.io/octave/function/linspace.html
% https://www.quora.com/How-does-Octaves-linspace-function-work-internally

% Plotting the function y = ax^2 + bx + c to display the qudratic function.
% Coefficients a, b, and c alter the steepness, horizontal shift, and verticle shift of the parabola.
xBeginning = -80;
xEnd = 80;
numberOfPoints = 200;
a = 1;
b = 0;
c = 0;
x = linspace(xBeginning, xEnd, numberOfPoints);
y = a * x.^2 + b * x + c;

figure(1,"position", [5,400,550,500]);
plot(x, y, ".r");
title("Graph of Function");
xlabel("x");
ylabel("y");
grid on;

% Salting and plotting the function with an adjustment range of 0-3500.
for i = 1:length(x)
    adjust = randi(3500);
    if randi([0, 1]) == 0
        y(i) += adjust;
    else
        y(i) -= adjust;
    end
end

figure(2,"position", [550,400,550,500]);
plot(x, y, ".m");
title(" Salted Graph of Function");
xlabel("x");
ylabel("y");
grid on;

% Smoothing and plotting the previously salted function with a window value of 60.
windowValue = 60;
average = movmean(y, windowValue);

figure(3,"position", [1100,400,550,500]);
plot(x, average, ".b");
title("Smoothed Graph of Function");
xlabel("x");
ylabel("y");
grid on;



