
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

//function to simulate the moisture accretion and depletion process in the soil slant during the light precipitation period
void fillslot_LP(double slots[],double PET,double precip, double WHC);
//overloaded function to simulate the moisture accretion and depletion process in the soil slant during the light precipitation period and calculate the amount of moisture accreted or depleted
void fillslot_LP(double slots[],double PET,double precip, double WHC, int SMC_r[]);
//function to simulate the moisture accretion and depletion process in the soil slant during the heavy precipitation period
void fillslot_HP(double slots[],double precip,double WHC);
//function to determine the moisture condition 
int moisture_CON(double a, double b, double c);
//function to calculate the evapotranspiration
void calpet(double MT[], double RLA, double APE[], char IH);
//function to calculate the soil temperature
void soil_temp(double soil_t[], double T[], char SN, double CC, double CD, double lag);
//function to determine the soil moisture and temperature regime
void determine_SMR(double soil_t[],int smc_t[],double precip[],double PET[],char SN,char STR_t[],char SMR_t[], char SMRS_t[],int bw_t[]);
//function to output the simulation results
void output(char year_t[],double mt[],double mprecip[],double mpet[],double slot_t[],int smc[],double soil_t[],char str[],char smr[],char smrs[],char station[],int bw_t[],char fn[]);




int main(int argc,char *argv[])
{
	FILE *fmont,*fmonp,*fpara,*fsmrs,*fsmr,*fwsmr,*fsummary;
//fmont: file pointer to open the monthly temperature file.
//fmonp: file pointer to open the monthly precipitation file
//fpara: file pointer to open the parameter file
//fsmrs: file pointer to open the soil moisture and temperautre regime subdivision file
//fsmr: file pointer to open the soil moisture and temperautre regime file
//fsummary: file pointer to open the summay of results file
	char montfn[180],monpfn[180],parafn[180],smrsfn[180],smrfn[180],wsmrfn[180],summaryfn[180],SFYEAR[180];
//variables holding path	
	char station[20]; 
//	station name
	char SouthNorth;
//south or north hemisphere
	double latitude;
//latitude
	double monthly_T[12], monthly_P[12],monthly_PET[12];
//monthly temperature, monthly precipitation and monthly evapotranspiration
	int i,j;
//loop counter
	char year[5];
//year
	double WTC;
//water holding capacity
	double slot[64]; 
//64 soil moisture slants
	double FSL;
//maximum water holding capacity of each slant
	double slotsum_old=0,slotsum_new=0,LP,HP;
//slotsum_old, slotsum_new: total moisture contents of all soil slants
//LP: light precipitation
//HP: heavy precipitation

	int SMC[360], SMC_temp[15];
//soil moisture calender

	double soil_T[360];
//soil temperature

	double T_avg, T_amplitude, T_lag;
//  T_avg: difference between annual average air temperature and soil temperature
//  T_amplitude: amplitude difference between air temperature and soil temperature
//  T_lag: phase lag between the air temperature and soil temperature

	char SMR[20],STR[20]="Undefined",SMRS[20];
//soil moisture regime definitions

	int bw[10];
//biological window

	char *soil_types[]={"UNDEFINED","PERUDIC","EXTREME ARIDIC","TYPIC ARIDIC","WEAK ARIDIC","DRY XERIC","TYPIC XERIC","ARIDIC TROPUSTIC","TYPIC TROPUSTIC","UDIC TROPUSTIC","XERIC TEMPUSTIC","WET TEMPUSTIC","TYPIC TEMPUSTIC","TYPIC UDIC","DRY TROPUDIC","DRY TEMPUDIC"};
	
	char *soil_type[]={"ARIDIC","XERIC","USTIC","PERUDIC","UDIC","UNDEFINED"};

	int STCs[16],STC[6];
//probabilities of soil moisture regimes
	int STs_count, ST_count;
	int total_year=0;
	double prectotal=0,pettotal=0,awb=0,msd=0;
	double precavg=0,petavg=0,awbavg=0,msdavg=0,bio51avg=0,bio52avg=0,bio53avg=0,bio8avg=0;
//variable need to store the statistics of simulation results

	if (argc>2)
	{
		strcpy(montfn,argv[2]);
		strcpy(monpfn,argv[2]);
		strcpy(parafn,argv[2]);
		strcpy(smrfn,argv[2]);
		strcpy(smrsfn,argv[2]);
		strcpy(wsmrfn,argv[2]);
		strcpy(summaryfn,argv[2]);
		strcpy(SFYEAR,argv[2]);
	}
	else
	{
		strcpy(montfn,"\0");
		strcpy(monpfn,"\0");
		strcpy(parafn,"\0");
		strcpy(smrfn,"\0");
		strcpy(smrsfn,"\0");
		strcpy(wsmrfn,"\0");
		strcpy(summaryfn,"\0");
		strcpy(SFYEAR,"\0");
	}

	strcat(montfn,"monthly_T");
	strcat(monpfn,"monthly_P");
	strcat(parafn,"parameter");
	strcat(summaryfn,"summary");

//open the input files and read the data into different variables
	if ((fmont=fopen(montfn,"r"))==NULL)
    {
        printf("Temperature input file opening failed.\n");
        exit(1);
	}

	if ((fmonp=fopen(monpfn,"r"))==NULL)
    {
        printf("Precipitation input file opening failed.\n");
        exit(1);
	}

	if ((fpara=fopen(parafn,"r"))==NULL)
    {
        printf("Parameter input file opening failed.\n");
        exit(1);
	}

	
	fscanf(fpara,"%lf  %c  %lf  %lf  %lf  %lf  %s",&latitude,&SouthNorth,&WTC, &T_avg, &T_amplitude, &T_lag, &station);
	fclose(fpara);

//initilaize variables
	for (i=0;i<16;i++)
			STCs[i]=0;

	for (i=0;i<6;i++)
			STC[i]=0;

//set the water holding capacity if it is different from the file
	if (argc>1)
		WTC=atof(argv[1]);
	else
		WTC=WTC*25.4;
 
	FSL=WTC/64.0;

//open the summary file and set the ouput format
	if ((fsummary=fopen(summaryfn,"w"))==NULL)
    {
        printf("Output file of Summary opening failed.\n");
        exit(1);
	}

	fprintf(fsummary,"YEAR  PREC  PET   AWB   MSD  Dry Days  M/D Days  Moist Days  BIO8  Soil Moisture Regime\n");
	fprintf(fsummary,"       mm    mm    mm    mm   Biological Windows at 5 degC\n\n");

//The while loop completes the simulation of all available years
	while (!feof(fmont))
	{
		fscanf(fmont,"%s  %lf  %lf  %lf  %lf  %lf  %lf", &year,&monthly_T[0],&monthly_T[1],&monthly_T[2],&monthly_T[3],&monthly_T[4],&monthly_T[5]);
		fscanf(fmont,"%lf  %lf  %lf  %lf  %lf  %lf", &monthly_T[6],&monthly_T[7],&monthly_T[8],&monthly_T[9],&monthly_T[10],&monthly_T[11]);

		fscanf(fmonp,"%s  %lf  %lf  %lf  %lf  %lf  %lf", &year,&monthly_P[0],&monthly_P[1],&monthly_P[2],&monthly_P[3],&monthly_P[4],&monthly_P[5]);
		fscanf(fmonp,"%lf  %lf  %lf  %lf  %lf  %lf", &monthly_P[6],&monthly_P[7],&monthly_P[8],&monthly_P[9],&monthly_P[10],&monthly_P[11]);
	
	
		for (i=0;i<12;i++)
			monthly_P[i]=monthly_P[i]*25.4;

		for (i=0;i<12;i++)
			monthly_T[i]=(monthly_T[i]-32.0)*5.0/9.0;


		for (i=0;i<64;i++)
			slot[i]=0;

//calculate the evapotranspiration

		calpet(monthly_T, latitude, monthly_PET, SouthNorth);

//do loop does a test run to reach the state that the soil moisture condition no longer changes over time and that condtion will be used as the intital condition
		do
		{
			slotsum_old=slotsum_new;
			slotsum_new=0;
			
			for (i=0;i<12;i++)
			{
				LP=monthly_P[i]/2.0;
				HP=monthly_P[i]/2.0;
				fillslot_LP(slot,monthly_PET[i],LP,FSL);
				fillslot_HP(slot,HP,FSL);
				fillslot_LP(slot,monthly_PET[i],LP,FSL);
			}

			for (i=0;i<64;i++)
				slotsum_new=slotsum_new+slot[i];

		}while (fabs(slotsum_old-slotsum_new)>(0.01*slotsum_old));



		for (i=0;i<15;i++)
			SMC_temp[i]=0;

		for (i=0;i<360;i++)
			SMC[i]=0;

//for loop completes the simulation for one year	
		for (i=0;i<12;i++)
		{
			//calculate the light and heavy precipitation
			LP=monthly_P[i]/2.0;
			HP=monthly_P[i]/2.0;
			//fill slots with light precipitation for the first half month
			fillslot_LP(slot,monthly_PET[i],LP,FSL,SMC_temp);
			//record the soil moisture condition for that period
			for (j=0;j<15;j++)
				SMC[i*30+j]=SMC_temp[j];
			//fill slots with heavy precipitation at the end of 15th day
			fillslot_HP(slot,HP,FSL);
			//fill slots with light precipitation for the second half month
			fillslot_LP(slot,monthly_PET[i],LP,FSL,SMC_temp);
			//record the soil moisture condition for that period
			for (j=0;j<15;j++)
				SMC[i*30+j+15]=SMC_temp[j];
		}

//calculate the soil temperaute for the whole year
		soil_temp(soil_T,monthly_T,SouthNorth,T_avg,T_amplitude,T_lag);
//determine the soil moisture and temperature calender 
		determine_SMR(soil_T,SMC,monthly_P,monthly_PET,SouthNorth,STR,SMR,SMRS,bw);
//output the simulation results 
		output(year,monthly_T,monthly_P,monthly_PET,slot,SMC,soil_T,STR,SMR,SMRS,station,bw,SFYEAR);

//calculate the statistics
		for (i=0;i<16;i++)
		{
			STs_count=strcmp(soil_types[i],SMRS);
			if (!STs_count)
				break;
		}
		
		STCs[i]++;
		
		for (i=0;i<6;i++)
		{
			ST_count=strcmp(soil_type[i],SMR);
			if (!ST_count)
				break;
		}
		
		STC[i]++;
		
		prectotal=0;
		pettotal=0;
		for (i=0;i<12;i++)
			prectotal+=monthly_P[i];
		for (i=0;i<12;i++)
			pettotal+=monthly_PET[i];
		awb=prectotal-pettotal;
		msd=monthly_P[5]+monthly_P[6]+monthly_P[7]-monthly_PET[5]-monthly_PET[6]-monthly_PET[7];
		
		fprintf(fsummary,"%s  %4.0lf  %3.0lf  %4.0lf  %4.0lf  %8d  %8d  %10d  %4d  %s\n",year,prectotal,pettotal,awb,msd,bw[3],bw[4],bw[5],bw[7],SMRS);
		
		precavg+=prectotal;
		petavg+=pettotal;
		awbavg+=awb;
		msdavg+=msd;
		bio51avg+=double (bw[3]);
		bio52avg+=double (bw[4]);
		bio53avg+=double (bw[5]);
		bio8avg+=double (bw[7]);
		total_year++;
	}

	fclose(fmont);
	fclose(fmonp);

	fprintf(fsummary,"\n AVG  %4.0lf  %3.0lf  %4.0lf  %4.0lf  %8.0lf  %8.0lf  %10.0lf  %4.0lf\n",precavg/total_year,petavg/total_year,awbavg/total_year,msdavg/total_year,bio51avg/total_year,bio52avg/total_year,bio53avg/total_year,bio8avg/total_year);
	fclose(fsummary);
//output the statistics	

	strcat(smrsfn,"SMRS");
	strcat(smrfn,"SMR");
	strcat(wsmrfn,"WSMR");

	if ((fsmrs=fopen(smrsfn,"w"))==NULL)
    {
        printf("Output file of SMRS opening failed.\n");
        exit(1);
	}

	if ((fsmr=fopen(smrfn,"w"))==NULL)
    {
        printf("Output file of SMR opening failed.\n");
        exit(1);
	}

	if ((fwsmr=fopen(wsmrfn,"w"))==NULL)
    {
        printf("Output file of WSMR opening failed.\n");
        exit(1);
	}


	fprintf(fsmr,"Years  Soil Moisture Regime  Frequency\n");
	for (i=0;i<6;i++)
		fprintf(fsmr,"%5d  %20s  %4.2lf\n",STC[i],soil_type[i],STC[i]*100.0/total_year);
	fprintf(fsmr,"\n%5d               %12s\n",total_year,"100");

	for (i=0;i<6;i++)
		fprintf(fwsmr,"%4.2lf,%10s\n",STC[i]*100.0/total_year,soil_type[i]);
	
	fprintf(fsmrs,"Years  Soil Moisture Regime  Frequency\n");
	for (i=0;i<16;i++)
		fprintf(fsmrs,"%5d  %20s  %4.2lf\n",STCs[i],soil_types[i],STCs[i]*100.0/total_year);
	fprintf(fsmrs,"\n%5d               %12s\n",total_year,"100");


	fclose(fsmr);
	fclose(fsmrs);
	fclose(fwsmr);
	
	printf("\nEnd of Program.\n");
    return 0;
}















//fill the slot with light precipitation

void fillslot_LP(double slots[],double PET,double precip, double WHC)
{
//accretion sequence
	int dp_s[]={8,7,16,6,15,24,5,14,23,32,4,13,22,31,40,3,12,
		  21,30,39,48,2,11,20,29,38,47,56,1,10,19,28,37,46,55,64,9,18,
		  27,36,45,54,63,17,26,35,44,53,62,25,34,43,52,61,33,42,51,60,
		  41,50,59,49,58,57};
//depletion sequence for sandy soils
	int dp_s_sandy[]={8,7,6,5,4,3,2,1,16,15,14,13,12,11,10,9,24,23,22,21,20,19,18,17,
				32,31,30,29,28,27,26,25,40,39,38,37,36,35,34,33,48,47,46,45,44,43,42,41,
				56,55,54,53,52,51,50,49,64,63,62,61,60,59,58,57};
//depletion sequence
    double dp_r[]={1.0,1.0,1.0,1.0,1.02,1.03,1.05,1.07,1.09,1.11,1.13,1.15,
          1.17,1.19,1.21,1.23,1.26,1.28,1.31,1.34,1.37,1.40,1.43,1.46,
          1.49,1.53,1.57,1.61,1.65,1.69,1.74,1.78,1.84,1.89,1.95,2.01,
          2.07,2.14,2.22,2.30,2.38,2.47,2.57,2.68,2.80,2.93,3.07,3.22,
          3.39,3.58,3.80,4.03,4.31,4.62,4.98,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0};
	double diff, net, residue, dp_amount;
	int i;
//calculate the difference between the precipitation and evapotranspiration

	if ((WHC*64)<100.0)
		for (i=0;i<64;i++)
			dp_s[i]=dp_s_sandy[i];

	diff=precip-PET;
	if (diff==0)
		return;
	else if (diff<0)
		net=diff*(-1)/2.0;
	else 
		net=diff/2.0;

//if precipitation exceeds the PET, involks the accretion process.  Otherwise, involks the depletion process
	if (diff>0)
	{
		i=0;
		while (i<64)
		{
			if(net<=0) break;
			//if slot is zero, just the fill the slot
			if (slots[i]==0)
			{
				residue=net-WHC;
				if (residue>=0)
				{
					slots[i]=WHC;
					net=net-WHC;
				}
				else 
				{
					slots[i]=net;
					net=0;
				}
			}
			//if slot is not zero, fill the difference
			else if ((slots[i]>0)&&(slots[i]<WHC))
			{
				residue=WHC-slots[i];
				if (net>=residue)
				{
					slots[i]=WHC;
					net=net-residue;
				}
				else
				{
					slots[i]=slots[i]+net;
					net=0;
				}
			}
			i++;
		}
	}
	
	else
	{
		i=0;
		while (i<64)
		{
			if(net<=0) break;
			//deplete the slot
			if (slots[dp_s[i]-1]!=0)
			{
				dp_amount=slots[dp_s[i]-1]*dp_r[i];
				net=net-dp_amount;
				if (net>=0)
					slots[dp_s[i]-1]=0.0;
				else
					slots[dp_s[i]-1]=slots[dp_s[i]-1]*fabs(net)/dp_amount;
			}
			i++;
		}
	}
			
}


//fill the slot with heavy precipitation.  The process is basicly the same as fill slots with the light precipitation


void fillslot_HP(double slots[],double precip,double WHC)
{
	double residue;
	int i=0;
	
	while (i<64)
	{
		if(precip<=0) break;
		if (slots[i]==0)
		{
			residue=precip-WHC;
			if (residue>=0)
			{
				slots[i]=WHC;
				precip=precip-WHC;
			}
			else 
			{
				slots[i]=precip;
				precip=0;
			}
		}
		else if ((slots[i]>0)&&(slots[i]<WHC))
		{
			residue=WHC-slots[i];
			if (precip>=residue)
			{
				slots[i]=WHC;
				precip=precip-residue;
			}
			else 
			{
				slots[i]=slots[i]+precip;
				precip=0;
			}
		}
		i++;
	}
}


//fill the slot with light precipitation and record the moisture condition

void fillslot_LP(double slots[],double PET,double precip, double WHC, int SMC_r[])
{

	int dp_s[]={8,7,16,6,15,24,5,14,23,32,4,13,22,31,40,3,12,
		  21,30,39,48,2,11,20,29,38,47,56,1,10,19,28,37,46,55,64,9,18,
		  27,36,45,54,63,17,26,35,44,53,62,25,34,43,52,61,33,42,51,60,
		  41,50,59,49,58,57};

	//depletion sequence for sandy soils
	int dp_s_sandy[]={8,7,6,5,4,3,2,1,16,15,14,13,12,11,10,9,24,23,22,21,20,19,18,17,
				32,31,30,29,28,27,26,25,40,39,38,37,36,35,34,33,48,47,46,45,44,43,42,41,
				56,55,54,53,52,51,50,49,64,63,62,61,60,59,58,57};

    double dp_r[]={1.0,1.0,1.0,1.0,1.02,1.03,1.05,1.07,1.09,1.11,1.13,1.15,
          1.17,1.19,1.21,1.23,1.26,1.28,1.31,1.34,1.37,1.40,1.43,1.46,
          1.49,1.53,1.57,1.61,1.65,1.69,1.74,1.78,1.84,1.89,1.95,2.01,
          2.07,2.14,2.22,2.30,2.38,2.47,2.57,2.68,2.80,2.93,3.07,3.22,
          3.39,3.58,3.80,4.03,4.31,4.62,4.98,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0,5.0};
//NPE, RPEX, RPEX1, RPEX2 store the amount of moisture actually used to accrete or delpete slots
	double diff, net, residue, dp_amount,NPE, RPEX=0,RPEX1=0, RPEX2=0;

	int i,SMD1,SMD2,SMD3, Mid, End;

	if ((WHC*64)<100.0)
		for (i=0;i<64;i++)
			dp_s[i]=dp_s_sandy[i];

	diff=precip-PET;
	if (diff==0)
	{
		SMD1=moisture_CON(slots[8],slots[16],slots[24]);
		for (i=0;i<15;i++)
			SMC_r[i]=SMD1;
		return;
	}
	else if (diff<0)
		net=diff*(-1)/2.0;
	else 
		net=diff/2.0;
	NPE=net;
	SMD1=moisture_CON(slots[8],slots[16],slots[24]);
	SMD2=SMD1;
	if (diff>0)
	{
		i=0;
		while (i<64)
		{
			if(net<=0) break;
			if (slots[i]==0)
			{
				residue=net-WHC;
				if (residue>=0)
				{
					slots[i]=WHC;
					net=net-WHC;
					RPEX=RPEX+WHC;
				}
				else 
				{
					slots[i]=net;
					net=0;
					RPEX=RPEX+net;
				}
			}
			else if ((slots[i]>0)&&(slots[i]<WHC))
			{
				residue=WHC-slots[i];
				if (net>=residue)
				{
					slots[i]=WHC;
					net=net-residue;
					RPEX=RPEX+residue;
				}
				else 
				{
					slots[i]=slots[i]+net;
					net=0;
					RPEX=RPEX+net;
				}
			}
		
			SMD3=moisture_CON(slots[8],slots[16],slots[24]);
			if (SMD2!=SMD3)
			{
				if (RPEX1==0)
					RPEX1=RPEX;
				else
					RPEX2=RPEX;
			RPEX=0;
			SMD2=SMD3;
			}
			i++;
		}
	}
	else
	{
		i=0;
		while (i<64)
		{
			if(net<=0) break;
			if (slots[dp_s[i]-1]!=0)
			{
				dp_amount=slots[dp_s[i]-1]*dp_r[i];
				net=net-dp_amount;
				if (net>=0)
				{
					slots[dp_s[i]-1]=0.0;
					RPEX=RPEX+dp_amount;
				}
				else
				{
					slots[dp_s[i]-1]=slots[dp_s[i]-1]*fabs(net)/dp_amount;
					RPEX=RPEX+fabs(net);
				}
			}
			SMD3=moisture_CON(slots[8],slots[16],slots[24]);
			if (SMD2!=SMD3)
			{
				if (RPEX1==0)
					RPEX1=RPEX;
				else
					RPEX2=RPEX;
			RPEX=0;
			SMD2=SMD3;
			}
			i++;
		}
	}
	

//determine the soil moisture classification from the soil moisture condtions
	if (abs(SMD3-SMD1)==2)
	{
		Mid=int (15.0*RPEX1/fabs(diff/2.0));
		End=int (15.0*RPEX2/fabs(diff/2.0));
		for (i=0;i<Mid;i++)
			SMC_r[i]=SMD1;
		for (i=Mid;i<Mid+End;i++)
			SMC_r[i]=2;
		for (i=End;i<15;i++)
			SMC_r[i]=SMD3;
	}
	else if (abs(SMD3-SMD1)==1)
	{
//		cout<<RPEX1;
		Mid=int (15.0*RPEX1/fabs(diff/2.0));
		for (i=0;i<Mid;i++)
			SMC_r[i]=SMD1;
		for (i=Mid;i<15;i++)
			SMC_r[i]=SMD3;
	}
	else 
	{
		for (i=0;i<15;i++)
			SMC_r[i]=SMD1;
	}
/*
	for (i=0;i<15;i++)
		cout<<SMC_r[i];
	cout<<"\n";
*/
}

//determine the moisture condition

//determine the soil moisture condition
int moisture_CON(double a, double b, double c)
{
	if ((a==0.0)&&(b==0.0)&&(c==0.0))
		return 1;
	else if ((a!=0.0)&&(b!=0.0)&&(c!=0.0))
		return 3;
	else
		return 2;
}


//calculate the potential evapotranspiration

void calpet(double MT[], double RLA, double APE[], char IH)
{

//  CALCULATION OF THORNTHWAITE EVAPOTRANSPIRATION

	double SWI, A;
    int KI, KK, KL, NROW, i;
//  double ZT[24], FN[31,12], RN[31], FS[13,12]
    double MWI[12],UPE[12];
    char  NO='N', SO='S';
    
//  INITIALIZE

    double ZPE[]={135.0,139.5,143.7,147.8,151.7,155.4,158.9,162.1,165.2,168.0,170.7,173.1,175.3,177.2,179.0,180.5,181.8,182.9,183.7,184.3,184.7,184.9,185.0,185.0};
    double ZT[]={26.5,27.,27.5,28.,28.5,29.,29.5,30.,30.5,31.,31.5,
	32.,32.5,33.,33.5,34.,34.5,35.,35.5,36.,36.5,37.,37.5,38.0};
//FN: coefficients for different latitude
    double FN[31][12]={	1.04,0.94,1.04,1.01,1.04,1.01,1.04,1.04,1.01,1.04,1.01,1.04,
						1.02,0.93,1.03,1.02,1.06,1.03,1.06,1.05,1.01,1.03,0.99,1.02,
						1.00,0.91,1.03,1.03,1.08,1.06,1.08,1.07,1.02,1.02,0.98,0.99,
						0.97,0.91,1.03,1.04,1.11,1.08,1.12,1.08,1.02,1.01,0.95,0.97,
						0.95,0.90,1.03,1.05,1.13,1.11,1.14,1.11,1.02,1.00,0.93,0.94,
						0.93,0.89,1.03,1.06,1.15,1.14,1.17,1.12,1.02,0.99,0.91,0.91,
						0.92,0.88,1.03,1.06,1.15,1.15,1.17,1.12,1.02,0.99,0.91,0.91,
						0.92,0.88,1.03,1.07,1.16,1.15,1.18,1.13,1.02,0.99,0.90,0.90,
						0.91,0.88,1.03,1.07,1.16,1.16,1.18,1.13,1.02,0.98,0.90,0.90,
						0.91,0.87,1.03,1.07,1.17,1.16,1.19,1.13,1.03,0.98,0.90,0.89,
						0.90,0.87,1.03,1.08,1.18,1.17,1.20,1.14,1.03,0.98,0.89,0.88,
						0.90,0.87,1.03,1.08,1.18,1.18,1.20,1.14,1.03,0.98,0.89,0.88,
						0.89,0.86,1.03,1.08,1.19,1.19,1.21,1.15,1.03,0.98,0.88,0.87,
						0.88,0.86,1.03,1.09,1.19,1.20,1.22,1.15,1.03,0.97,0.88,0.86,
						0.88,0.85,1.03,1.09,1.20,1.20,1.22,1.16,1.03,0.97,0.87,0.86,
						0.87,0.85,1.03,1.09,1.21,1.21,1.23,1.16,1.03,0.97,0.86,0.85,
						0.87,0.85,1.03,1.10,1.21,1.22,1.24,1.16,1.03,0.97,0.86,0.84,
						0.86,0.84,1.03,1.10,1.22,1.23,1.25,1.17,1.03,0.97,0.85,0.83,
						0.85,0.84,1.03,1.10,1.23,1.24,1.25,1.17,1.04,0.96,0.84,0.83,
						0.85,0.84,1.03,1.11,1.23,1.24,1.26,1.18,1.04,0.96,0.84,0.82,
						0.84,0.83,1.03,1.11,1.24,1.25,1.27,1.18,1.04,0.96,0.83,0.81,
						0.83,0.83,1.03,1.11,1.25,1.26,1.27,1.19,1.04,0.96,0.82,0.80,
						0.82,0.83,1.03,1.12,1.26,1.27,1.28,1.19,1.04,0.95,0.82,0.79,
						0.81,0.82,1.02,1.12,1.26,1.28,1.29,1.20,1.04,0.95,0.81,0.77,
						0.81,0.82,1.02,1.13,1.27,1.29,1.30,1.20,1.04,0.95,0.80,0.76,
						0.80,0.81,1.02,1.13,1.28,1.29,1.31,1.21,1.04,0.94,0.79,0.75,
						0.79,0.81,1.02,1.13,1.29,1.31,1.32,1.22,1.04,0.94,0.79,0.74,
						0.77,0.80,1.02,1.14,1.30,1.32,1.33,1.22,1.04,0.93,0.78,0.73,
						0.76,0.80,1.02,1.14,1.31,1.33,1.34,1.23,1.05,0.93,0.77,0.72,
						0.75,0.79,1.02,1.14,1.32,1.34,1.35,1.24,1.05,0.93,0.76,0.71,
						0.74,0.78,1.02,1.15,1.33,1.36,1.37,1.25,1.06,0.92,0.76,0.70};
	double RN[]={0.,5.,10.,15.,20.,25.,26.,27.,28.,29.,30.,31.,32.,33.,
     34.,35.,36.,37.,38.,39.,40.,41.,42.,43.,44.,45.,46.,47.,48.,49.,
     50.};
    double FS[13][12]={	1.06,0.95,1.04,1.00,1.02,0.99,1.02,1.03,1.00,1.05,1.03,1.06,
						1.08,0.97,1.05,0.99,1.01,0.96,1.00,1.01,1.00,1.06,1.05,1.10,
						1.12,0.98,1.05,0.98,0.98,0.94,0.97,1.00,1.00,1.07,1.07,1.12,
						1.14,1.00,1.05,0.97,0.96,0.91,0.95,0.99,1.00,1.08,1.09,1.15,
						1.17,1.01,1.05,0.96,0.94,0.88,0.93,0.98,1.00,1.10,1.11,1.18,
						1.20,1.03,1.06,0.95,0.92,0.85,0.90,0.96,1.00,1.12,1.14,1.21,
						1.23,1.04,1.06,0.94,0.89,0.82,0.87,0.94,1.00,1.13,1.17,1.25,
						1.27,1.06,1.07,0.93,0.86,0.78,0.84,0.92,1.00,1.15,1.20,1.29,
						1.28,1.07,1.07,0.92,0.85,0.76,0.82,0.92,1.00,1.16,1.22,1.31,
						1.30,1.08,1.07,0.92,0.83,0.74,0.81,0.91,0.99,1.17,1.23,1.33,
						1.32,1.10,1.07,0.91,0.82,0.72,0.79,0.90,0.99,1.17,1.25,1.35,
						1.34,1.11,1.08,0.90,0.80,0.70,0.76,0.89,0.99,1.18,1.27,1.37,
						1.37,1.12,1.08,0.89,0.77,0.67,0.74,0.88,0.99,1.19,1.29,1.41};
	double RS[]={5.,10.,15.,20.,25.,30.,35.,40.,42.,44.,46.,48.,50};
	double CF;

//   UPE  UNADJUSTED POT.EVAP.
//   APE  ADJUSTED EVAPOTRANSP.
//   MWI  MONTHLY HEAT INDEX
	for (i=0;i<12;i++)
	{
		UPE[i]=0;
        APE[i]=0;
        MWI[i]=0;
	}
//  CALCULATE HEAT INDEX

//  RLA=double(RLA1)+double(RLA2)/60.0;
    A=0;
    SWI=0;

	for (i=0;i<12;i++)
	 {
		if (MT[i]>0) 
        MWI[i]=pow(MT[i]/5.0,1.514);
	 }

    for (i=0;i<12;i++)
		SWI=SWI+MWI[i];
	    A=(0.000000675*pow(SWI,3))-(0.0000771*pow(SWI,2))+(0.01792*SWI)+0.49239;


// CALCULATE UNADJ.POT.EVAP.
	
	for (i=0;i<12;i++)
	{
		if (MT[i]>=38)
			UPE[i]=185;
		else if ((MT[i]<38)&&(MT[i]>=26.5))
		{
			for (KI=0;KI<24;KI++)
			{
				KL=KI+1;
				KK=KI;
				if ((MT[i]>=ZT[KI])&&(MT[i]<ZT[KL]))
					break;
			}

			UPE[i]=ZPE[KK];
		}
		else if ((MT[i]>0)&&(MT[i]<=26.5))
			UPE[i]=16*pow(10*MT[i]/SWI,A);
	}



	if (IH==NO)
	{
//  ADJUST FOR NORTHERN HEMISPHERE

		NROW=0;
		for (i=0;i<31;i++)
		{
			if (RLA < RN[i]) 
				break;
			NROW++;
		}

		for (i=0;i<12;i++)
		{
            if (UPE[i]>=0)
				APE[i]=UPE[i]*FN[NROW-1][i];
		}
	}
//   ADJUST FOR SOUTH HEMISPHERE
	else if (IH==SO)
	{
		NROW=0;
		for (i=0;i<13;i++)
		{
			if (RLA<RS[i])
				break;
			NROW++;
		}
		
		if (NROW==0)
		{
			for(i=0;i<12;i++)
			{
				if (UPE[i]>0)
				{
					CF=(FS[0][i]-FN[0][i])*(RLA*60.0)/300;
					CF=CF+FN[0][i];
					APE[i]=UPE[i]*CF;
				}
			}
		}
		else 
		{
			for(i=0;i<12;i++)
			{
				if (NROW>=12)
					CF=FS[12][i];
				else
				{	
					CF=((FS[NROW][i]-FS[NROW-1][i])*(RLA-RS[NROW-1])/((RS[NROW]-RS[NROW-1])*60.0));
					CF=FS[NROW-1][i]+CF;
				}
				
				APE[i]=UPE[i]*CF;
	
			}
		}

	}
}



void determine_SMR(double soil_t[],int smc_t[],double precip[],double PET[],char SN,char STR_t[],char SMR_t[], char SMRS_t[], int bw_t[])
{
	int one=0,two=0,three=0,four=0,five=0,six=0,seven=0,eight=0,nine=0;
	int i;
//t5:number of days that soil temperature is above 5 degree C
//t8:number of days that soil temperature is above 5 degree C
	int t5=0,t8=0;
//dd:number of days that MCS is dry;
//dm:number of days that MCS is moist;
//db:number of days that MCS is partly dry and partly moist;
	int dd=0,dm=0,db=0;
//tm8:number of consecutive days that soil tempature is over 8 C and MCS is partly moist or moist for 90;
//td:number of days that MCS is dry when soil temperature is more over 5 C
	int td=0,tb=0,tm=0,tm8=0,tmy=0,iso=0; 
//st_sum:mean soil temperature for one year
//winter_avg: sum of soil temperature for the winter
//summer_avg: sum of soil temperature for the summer
//sd:number of consecutive days that MCS is dry within four month following the summer solstice
//snd:number of consecutive days that MCS is not completely dry within four month following the summer solstice
//wm:number of consecutive days that MCS is moist within four month following the winter solstice
//comd:number of consecutive days that MCS is completely dry or partly dry in one year
	double st_annual_mean=0,winter_avg=0,summer_avg=0,swdiff;
	
	int sd=0,snd=0,wm=0,comd=0,max;

/*
	FILE *ftest;
ftest=fopen("test","w");
	fprintf(ftest,"%s\n   ","Soil Moisture Calender:");
	for (i=0;i<30;i++)
		fprintf(ftest,"%2d ",i+1);
	for (i=0;i<360;i++)
	{
		if (i%30)
			fprintf(ftest,"%2d ",smc_t[i]);
		else
		{
			fprintf(ftest,"\n%2d ",i/30+1);
			fprintf(ftest,"%2d ",smc_t[i]);
		}
	}	
fclose(ftest);
	
*/	
	for (i=0;i<360;i++)
		if (smc_t[i]==1)
			dd++;
		else if (smc_t[i]==2)
			db++;
		else if (smc_t[i]==3)
			dm++;
	
	bw_t[0]=dd;
	bw_t[1]=db;
	bw_t[2]=dm;

	for (i=0;i<360;i++)
	{
		if (soil_t[i]>5)
			t5++;
		if (soil_t[i]>8)
			t8++;
	}
//Condition No 1. 	
	for (i=0;i<360;i++)
	{
		if ((smc_t[i]==1)&&(soil_t[i]>5))
			td++;
		if ((smc_t[i]==2)&&(soil_t[i]>5))
			tb++;
		if ((smc_t[i]==3)&&(soil_t[i]>5))
			tm++;
	}

	bw_t[3]=td;
	bw_t[4]=tb;
	bw_t[5]=tm;

	if (td>=(t5/2))
		one=1;
//Condition	No 2. 
	max=0;
	for (i=0;i<360;i++)
	{
		if ((soil_t[i]>8)&&(smc_t[i]!=1))
			tm8++;
		else
		{
			if (tm8>max)
				max=tm8;
			tm8=0;
		}
	}
	if (tm8<max)
		tm8=max;

//	j=smc_t[0];
	max=0;
	for (i=0;i<360;i++)
	{
		if (smc_t[i]!=1)
			tmy++;
		else
		{
			if (tmy>max)
				max=tmy;
			tmy=0;
		}
	}
	if (tmy<max)
		tmy=max;

	bw_t[6]=tmy;
	bw_t[7]=tm8;

	if (tm8>=90)
		two=1;
//Condition	No 3. 
	for (i=0;i<360;i++)
		st_annual_mean+=soil_t[i];
	st_annual_mean=st_annual_mean/360.0;
	
	if (st_annual_mean<22)
		three=1;
//Condition	No 4.
	
	for (i=150;i<240;i++)
		summer_avg+=soil_t[i];
	for (i=0;i<60;i++)
		winter_avg+=soil_t[i];
	for (i=330;i<360;i++)
		winter_avg+=soil_t[i];

	summer_avg=summer_avg/90.0;
	winter_avg=winter_avg/90.0;
	swdiff=fabs(summer_avg-winter_avg);
	
	if (swdiff>=5)
		four=1;
	else
		iso=1;
//Condition	No. 5 for north hemisphere and 6 for south hemisphere.
	
	max=0;
	for (i=180;i<300;i++)
	{
		if (SN=='N')
		{
			if (smc_t[i]==1)
				sd++;
			else
			{
				if (sd>max)
					max=sd;
				sd=0;
			}

		}
		else if (SN=='S')
		{
			if (smc_t[i]==3)
				wm++;
			else
			{
				if (wm>max)
					max=wm;
				wm=0;
			}
		}
	}
	
	if (SN=='N')
	{
		if (sd<max)
			sd=max;
		bw_t[8]=sd;
		if (sd>=45)
			five=1;
	}
	else if (SN=='S')
	{
		if (wm<max)
			wm=max;
		bw_t[9]=wm;
		if (wm>=45)
			six=1;
	}
//Condition No.6 for north and 5 for south

	max=0;
	for (i=0;i<120;i++)
	{
		if (SN=='N')
		{
			if (smc_t[i]==3)
				wm++;
			else
			{
				if (wm>max)
					max=wm;
				wm=0;
			}
		}
		else if (SN=='S')
		{
			if (smc_t[i]==1)
				sd++;
			else
			{
				if (sd>max)
					max=sd;
				sd=0;
			}
		}
	}
	
	if (SN=='N')
	{	
		if (wm<max)
			wm=max;
		bw_t[9]=wm;
		if (wm>=45)
			six=1;
	}
	else if (SN=='S')
	{
		if (sd<max)
			sd=max;
		bw_t[8]=sd;
		if (sd>=45)
			five=1;
	}

//Condition No.7

	max=0;
	for (i=0;i<360;i++)
	{
		if ((smc_t[i]==1)||(smc_t[i]==2))
			comd++;
		else
		{
			if (comd>max)
				max=comd;
			comd=0;
		}
	}
	if (comd<max)
		comd=max;
	if (comd>=90)
		seven=1;
//Condition No.8
	if (st_annual_mean>=8)
		eight=1;
//Condition No.9
	for (i=0;i<12;i++)
		if ((precip[i]-PET[i])<=0)
			break;
	if (i==12)
		nine=1;

//determining soil moisture regime

	if (one)
		goto c2;
	else
		goto c3;
c2:
	if (two)
		goto c3;
	else
	{
		strcpy(SMR_t,"ARIDIC");
		goto ARIDIC;
	}
c3:
	if (three)
		goto c4;
	else 
		goto c7;
c4:
	if (four)
		goto c5;
	else 
		goto c7;
c5:
	if (five)
		goto c6;
	else 
		goto c7;
c6:
	if (six)
	{
		strcpy(SMR_t,"XERIC");
		goto XERIC;
	}
	else
		goto c7;
c7:
	if (seven)
		goto c8;
	else
		goto c9;
c8:
	if (eight)
	{
		strcpy(SMR_t,"USTIC");
		goto USTIC;
	}
	else
	{
		strcpy(SMR_t,"UNDEFINED");
		strcpy(SMRS_t,"UNDEFINED");
		goto ENDofSMR;
	}
c9:
	if (nine)
	{
		strcpy(SMR_t,"PERUDIC");
		strcpy(SMRS_t,"PERUDIC");
		goto ENDofSMR;
	}
	else
	{
		strcpy(SMR_t,"UDIC");
		goto UDIC;
	}
ARIDIC:
	for (i=0;i<360;i++)
		if (smc_t[i]!=1)
			break;
	if (i==360)
		strcpy(SMRS_t,"EXTREME ARIDIC");
	else if (tm8<=45)
		strcpy(SMRS_t,"TYPIC ARIDIC");
	else
		strcpy(SMRS_t,"WEAK ARIDIC");
	
	goto ENDofSMR;
XERIC:
	if (sd>90)
		strcpy(SMRS_t,"DRY XERIC");
	else
		strcpy(SMRS_t,"TYPIC XERIC");
	goto ENDofSMR;
USTIC:

	max=0;
	for (i=180;i<300;i++)
	{
		if (smc_t[i]!=1)
			snd++;
		else
		{
			if (snd>max)
				max=snd;
			snd=0;
		}
	}
	if (snd<max)
		snd=max;
	
	if (iso)
	{
		if (tm8<180)
			strcpy(SMRS_t,"ARIDIC TROPUSTIC");
		else if ((tm8>=180)&&(tm8<270))
			strcpy(SMRS_t,"TYPIC TROPUSTIC");
		else if (tm8>=270)
			strcpy(SMRS_t,"UDIC TROPUSTIC");
	}
	else
	{
		if ((sd>45)&&(wm>45))
			strcpy(SMRS_t,"XERIC TEMPUSTIC");
		else if ((wm>45)&&(snd>45))
			strcpy(SMRS_t,"WET TEMPUSTIC");
		else
			strcpy(SMRS_t,"TYPIC TEMPUSTIC");
	}

	goto ENDofSMR;
	
UDIC:
	if (comd<30)
		strcpy(SMRS_t,"TYPIC UDIC");
	else if (iso)
		strcpy(SMRS_t,"DRY TROPUDIC");
	else
		strcpy(SMRS_t,"DRY TEMPUDIC");

ENDofSMR:

	if (st_annual_mean<0)
		strcpy(STR_t,"Pergelic");

	if ((st_annual_mean>=0)&&(st_annual_mean<8))
		strcpy(STR_t,"Cryic");

	if (st_annual_mean<8)
		if (iso)
			strcpy(STR_t,"Isofrigid");
		else
			strcpy(STR_t,"Frigid");

	if ((st_annual_mean>=8)&&(st_annual_mean<15))
		if (iso)
			strcpy(STR_t,"Isomesic");
		else
			strcpy(STR_t,"Mesic");

	if ((st_annual_mean>=15)&&(st_annual_mean<22))
		if (iso)
			strcpy(STR_t,"Isothermic");
		else
			strcpy(STR_t,"Thermic");

	if (st_annual_mean>=22)
		if (iso)
			strcpy(STR_t,"Isohyperthermic");
		else
			strcpy(STR_t,"Hyperthermic");

}


	
void soil_temp(double soil_t[], double T[], char SN, double CC, double CD, double lag)
{
	double year_AVG=0, A;
	//CC: average temprature adjustment
	//CD: amplitude adjustment
	//lag1, lag2: phase lag adjustment
	double lag1, lag2, w;
	double soil_t_temp[360];
	int i;
	double summer_AVG,winter_AVG;

	lag1=lag;
	lag2=lag;

	for (i=0;i<12;i++)
		year_AVG+=T[i];

	year_AVG=year_AVG/12.0+CC;

	summer_AVG=(T[5]+T[6]+T[7])/3.0;
	
	winter_AVG=(T[0]+T[1]+T[11])/3.0;

//	A=fabs(T[6]-T[0])/2.0*CD;

	A=fabs(summer_AVG-winter_AVG)/2.0*CD;
	
	w=2.0*3.1415926/360.0;

	for(i=0;i<360;i++)
		soil_t_temp[i]=0;

	for(i=0;i<360;i++)
	{
		if (SN=='N')
		{
			if ((i>=90)&&(i<270)) 
				soil_t_temp[i]=year_AVG+A*sin(w*(i+lag2));
			else
				soil_t_temp[i]=year_AVG+A*sin(w*(i+lag1));
		}
		else
		{
			if ((i>=90)&&(i<270)) 
				soil_t_temp[i]=year_AVG+A*cos(w*(i+lag2));
			soil_t_temp[i]=year_AVG+A*cos(w*(i+lag1));
		}
	}

	for (i=0;i<134;i++)
		soil_t[i]=soil_t_temp[i+226];

	for (i=134;i<360;i++)
		soil_t[i]=soil_t_temp[i-134];
}

void output(char year_t[],double mt[],double mprecip[],double mpet[],double slot_t[],int smc[],double soil_t[],char str[],char smr[],char smrs[],char station[],int bw_t[],char fn[])
{
	FILE *fout;
	//file pointer of output file named after the year
	int i;
	char st58[360];
	char outfn[80];
		
	strcpy(outfn,fn);
	strcat(outfn,year_t);
	printf("%s\n",year_t);
	
	if ((fout=fopen(outfn,"w"))==NULL)
	{
		printf("Output file opening failed.\n");
		exit(1);
	}
	
	fprintf(fout,"The simulation results of Newhall Model for Station %s in %s\n\n",station,year_t);

	fprintf(fout,"%s\n","Monthly Temperature (degree Celsius):");
	for (i=0;i<12;i++)
		fprintf(fout,"%8d",i+1);
	fprintf(fout,"\n");
	for (i=0;i<12;i++)
		fprintf(fout,"%8.1lf",mt[i]);	
	fprintf(fout,"\n\n");

	fprintf(fout,"%s\n","Monthly Precipitation (mm):");
	for (i=0;i<12;i++)
		fprintf(fout,"%8d",i+1);
	fprintf(fout,"\n");
	for (i=0;i<12;i++)
		fprintf(fout,"%8.1lf",mprecip[i]);
	fprintf(fout,"\n\n");

	fprintf(fout,"%s\n","Monthly PET (mm):");
	for (i=0;i<12;i++)
		fprintf(fout,"%8d",i+1);
	fprintf(fout,"\n");
	for (i=0;i<12;i++)
		fprintf(fout,"%8.1lf",mpet[i]);
	fprintf(fout,"\n\n");
	
	fprintf(fout,"%s\n      ","Soil Moisture (mm) in every slant at the end of the year:");
	for (i=0;i<8;i++)
		fprintf(fout,"%6d  ",i+1);
	for (i=0;i<64;i++)
	{
		if (i%8)
			fprintf(fout,"%6.3lf  ",slot_t[i]);
		else 
		{
			fprintf(fout,"\n%6d  ",i/8+1);
			fprintf(fout,"%6.3lf  ",slot_t[i]);
		}
	}
	fprintf(fout,"\n\n");

	for (i=0;i<360;i++)
	{
		if (soil_t[i]<5)
			st58[i]='-';
		if ((soil_t[i]>=5)&&(soil_t[i]<8))
			st58[i]='5';
		if (soil_t[i]>=8)
			st58[i]='8';
	}
	
	fprintf(fout,"%s\n   ","Soil Temperature Calender:");
	for (i=0;i<30;i++)
		fprintf(fout,"%2d ",i+1);
	for (i=0;i<360;i++)
	{
		if (i%30)
			fprintf(fout,"%2c ",st58[i]);
		else
		{
			fprintf(fout,"\n%2d ",i/30+1);
			fprintf(fout,"%2c ",st58[i]);
		}
	}	
	fprintf(fout,"\n-: T<5  5: 5<=T<8 8: T>=8\n\n");	


	fprintf(fout,"%s\n   ","Soil Moisture Calender:");
	for (i=0;i<30;i++)
		fprintf(fout,"%2d ",i+1);
	for (i=0;i<360;i++)
	{
		if (i%30)
			fprintf(fout,"%2d ",smc[i]);
		else
		{
			fprintf(fout,"\n%2d ",i/30+1);
			fprintf(fout,"%2d ",smc[i]);
		}
	}	
	fprintf(fout,"\n1: Dry  2: Partly Dry and Partly Moist  3: Moist\n\n");	

	fprintf(fout,"%s\n   ","Soil Temperature Regime:");
	fprintf(fout,"%s\n\n",str);
	
	fprintf(fout,"%s\n   ","Soil Moisture Regime:");
	fprintf(fout,"%s\n\n",smr);

	fprintf(fout,"%s\n   ","Tentative Subdivision of Soil Moisture Regime:");
	fprintf(fout,"%s\n\n",smrs);

	fprintf(fout,"Number of cumulative days that the MCS during one year\n");
	fprintf(fout,"       DRY               M/D               Moist\n");
	fprintf(fout,"       %3d               %3d               %3d\n\n",bw_t[0],bw_t[1],bw_t[2]);

	fprintf(fout,"Number of cumulative days that the MCS when soil temperature is above 5 degree C\n");
	fprintf(fout,"       DRY               M/D               Moist\n");
	fprintf(fout,"       %3d               %3d               %3d\n\n",bw_t[3],bw_t[4],bw_t[5]);

	fprintf(fout,"Highest number of consecutive days that the MCS is Moist in some parts\n");
	fprintf(fout,"               Year               T>8\n");
	fprintf(fout,"               %3d               %3d\n\n",bw_t[6],bw_t[7]);

	fprintf(fout,"Highest number of consecutive days that the MCS is\n");
	fprintf(fout,"Dry after Summer Solstice  Moist after Winter Solstice\n");
	fprintf(fout,"           %3d                          %3d\n\n",bw_t[8],bw_t[9]);

	fclose(fout);
}